package br.com.leitura_imagens;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;


public class ProcessadorImagem implements Observador 
{
    private final MonitorPastaImagem sujeito;

    public ProcessadorImagem(MonitorPastaImagem sujeito) 
    {
        this.sujeito = sujeito;
    }

    @Override
    public void atualizar() 
    {
        try 
        {
            String nomeArquivo = sujeito.getNome();
            String pasta = sujeito.getPastaPath();
            File imagemFile = new File(pasta + File.separator + nomeArquivo);
            if (!imagemFile.exists()) {
                System.out.println("Imagem não encontrada: " + imagemFile.getAbsolutePath());
                return;
            }

            BufferedImage imagem = ImageIO.read(imagemFile);

            // tentar múltiplos recortes (fallbacks) baseado nas dimensões da imagem
            int w = imagem.getWidth();
            int h = imagem.getHeight();

            // definições relativas para tentar encontrar o display do odômetro
            int[][] crops = new int[][]{
                    // x, y, width, height (valores relativos serão convertidos abaixo)
                    { (int)(w*0.14), (int)(h*0.08), (int)(w*0.72), (int)(h*0.12) },
                    { (int)(w*0.20), (int)(h*0.10), (int)(w*0.60), (int)(h*0.10) },
                    { (int)(w*0.25), (int)(h*0.12), (int)(w*0.50), (int)(h*0.10) },
                    { 300, 120, 400, 80 } // fallback hardcoded (antigo)
            };

            BufferedImage scaled = null;
            String ocrResult = null;

            for (int[] c : crops) {
                int cx = Math.max(0, Math.min(c[0], w-1));
                int cy = Math.max(0, Math.min(c[1], h-1));
                int cw = Math.max(1, Math.min(c[2], w-cx));
                int ch = Math.max(1, Math.min(c[3], h-cy));

                BufferedImage display = imagem.getSubimage(cx, cy, cw, ch);

                // Pré-processamento: converter para grayscale e aumentar escala para melhorar OCR
                BufferedImage gray = new BufferedImage(display.getWidth(), display.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
                Graphics2D g = gray.createGraphics();
                g.drawImage(display, 0, 0, null);
                g.dispose();

                int scaledW = gray.getWidth() * 2;
                int scaledH = gray.getHeight() * 2;
                Image tmp = gray.getScaledInstance(scaledW, scaledH, Image.SCALE_SMOOTH);
                scaled = new BufferedImage(scaledW, scaledH, BufferedImage.TYPE_BYTE_GRAY);
                Graphics2D g2 = scaled.createGraphics();
                g2.drawImage(tmp, 0, 0, null);
                g2.dispose();

                // opcional: binarizar (threshold) para reduzir ruído
                int threshold = 150;
                for (int y2 = 0; y2 < scaled.getHeight(); y2++) {
                    for (int x2 = 0; x2 < scaled.getWidth(); x2++) {
                        int rgb = scaled.getRGB(x2, y2);
                        int grayVal = rgb & 0xFF;
                        int newVal = (grayVal < threshold) ? 0x00 : 0xFFFFFF;
                        scaled.setRGB(x2, y2, (0xFF<<24) | newVal);
                    }
                }

                System.out.println("Tentando recorte: x="+cx+" y="+cy+" w="+cw+" h="+ch);

                // tentar OCR nesse recorte
                ITesseract tesseractTry = new Tesseract();
                tesseractTry.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
                tesseractTry.setLanguage("eng");
                tesseractTry.setVariable("tessedit_char_whitelist", "0123456789.");

                try {
                    String textoTry = tesseractTry.doOCR(scaled);
                    System.out.println("OCR raw tentativa: ["+textoTry+"]");
                    String limpoTry = textoTry.replaceAll("[^0-9.]", "");
                    System.out.println("OCR limpo tentativa: ["+limpoTry+"]");
                    if (limpoTry != null && !limpoTry.isEmpty()) {
                        ocrResult = limpoTry;
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Erro OCR tentativa: " + e.getMessage());
                }
            }

            // 🔹 2. Configuração do Tesseract
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
            tesseract.setLanguage("eng");

            // só números e ponto
            tesseract.setVariable("tessedit_char_whitelist", "0123456789.");

            System.out.println("Processando arquivo: " + imagemFile.getAbsolutePath());
            if (ocrResult == null) {
                // última tentativa com o scaled gerado
                try {
                    String texto = tesseract.doOCR(scaled);
                    System.out.println("OCR raw fallback: [" + texto + "]");
                    ocrResult = texto.replaceAll("[^0-9.]", "");
                    System.out.println("OCR limpo fallback: [" + ocrResult + "]");
                } catch (Exception e) {
                    System.out.println("OCR fallback erro: " + e.getMessage());
                }
            }

            if (ocrResult == null || ocrResult.isEmpty()) {
                System.out.println("Nenhum texto numérico detectado.");
                return;
            }

            try {
                double vol = Double.parseDouble(ocrResult);
                sujeito.setVolume(vol);
                System.out.println("Volume detectado: " + vol);
            } catch (NumberFormatException nfe) {
                System.out.println("Não foi possível converter para double: " + ocrResult);
            }
        } 
        catch (IOException e) {
            System.out.println("Erro lendo imagem: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erro processando imagem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
