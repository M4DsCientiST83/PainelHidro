package br.com.leitura_imagens;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class ProcessadorImagem implements Observador {
    private final MonitorPastaImagem sujeito;
    private final HidrometroAdapter adapter;

    public ProcessadorImagem(MonitorPastaImagem sujeito, char tipoHidrometro) {
        this.sujeito = sujeito;
        this.adapter = criarAdapter(tipoHidrometro);
    }

    private HidrometroAdapter criarAdapter(char tipo) {
        return switch (tipo) {
            case 'B' -> new HidrometroSimplexAdapter();
            default -> new HidrometroDigitalAdapter(); // 'A' ou qualquer outro = tipo A
        };
    }

    @Override
    public void atualizar() {
        try {
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

            // Obter regiões de recorte do adapter
            int[][] crops = adapter.getCropRegions(w, h);

            BufferedImage scaled = null;
            String ocrResult = null;

            for (int[] c : crops) {
                int cx = Math.max(0, Math.min(c[0], w - 1));
                int cy = Math.max(0, Math.min(c[1], h - 1));
                int cw = Math.max(1, Math.min(c[2], w - cx));
                int ch = Math.max(1, Math.min(c[3], h - cy));

                BufferedImage display = imagem.getSubimage(cx, cy, cw, ch);

                // Pré-processamento: converter para grayscale e aumentar escala para melhorar
                // OCR
                BufferedImage gray = new BufferedImage(display.getWidth(), display.getHeight(),
                        BufferedImage.TYPE_BYTE_GRAY);
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

                // DESABILITADO: binarização estava removendo o texto
                // int threshold = 150;
                // for (int y2 = 0; y2 < scaled.getHeight(); y2++) {
                // for (int x2 = 0; x2 < scaled.getWidth(); x2++) {
                // int rgb = scaled.getRGB(x2, y2);
                // int grayVal = rgb & 0xFF;
                // int newVal = (grayVal < threshold) ? 0x00 : 0xFFFFFF;
                // scaled.setRGB(x2, y2, (0xFF<<24) | newVal);
                // }
                // }

                // tentar OCR nesse recorte
                ITesseract tesseractTry = new Tesseract();
                tesseractTry.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
                tesseractTry.setLanguage("eng");
                tesseractTry.setVariable("tessedit_char_whitelist", "0123456789.");

                try {
                    String textoTry = tesseractTry.doOCR(scaled);
                    // System.out.println("OCR raw tentativa: [" + textoTry + "]");
                    String limpoTry = textoTry.replaceAll("[^0-9.]", "");
                    // System.out.println("OCR limpo tentativa: [" + limpoTry + "]");
                    if (limpoTry != null && !limpoTry.isEmpty()) {
                        ocrResult = limpoTry;
                        break;
                    }
                } catch (Exception e) {
                    // System.out.println("Erro OCR tentativa: " + e.getMessage());
                }
            }

            // 🔹 2. Configuração do Tesseract
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
            tesseract.setLanguage("eng");

            // só números e ponto
            tesseract.setVariable("tessedit_char_whitelist", "0123456789.");

            // System.out.println("Processando arquivo: " + imagemFile.getAbsolutePath());
            if (ocrResult == null) {
                // última tentativa com o scaled gerado
                try {
                    String texto = tesseract.doOCR(scaled);
                    // System.out.println("OCR raw fallback: [" + texto + "]");
                    ocrResult = texto.replaceAll("[^0-9.]", "");
                    // System.out.println("OCR limpo fallback: [" + ocrResult + "]");
                } catch (Exception e) {
                    // System.out.println("OCR fallback erro: " + e.getMessage());
                }
            }

            if (ocrResult == null || ocrResult.isEmpty()) {
                // System.out.println("Nenhum texto numérico detectado.");
                return;
            }

            try {
                // Usar o adapter para processar o volume de acordo com o tipo de hidrômetro
                double vol = adapter.processarVolume(ocrResult);
                sujeito.setVolume(vol);
                // System.out.println("Volume detectado: " + vol);
                // System.out.println("Volume definido no monitor: " + sujeito.getVolume());
            } catch (NumberFormatException nfe) {
                System.out.println("Não foi possível converter para double: " + ocrResult);
            }
        } catch (IOException e) {
            System.out.println("Erro lendo imagem: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erro processando imagem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
