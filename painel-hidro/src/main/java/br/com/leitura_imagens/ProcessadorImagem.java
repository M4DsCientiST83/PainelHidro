package br.com.leitura_imagens;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


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
            File imagemFile = new File("C:/imagens/" + nomeArquivo);

            BufferedImage imagem = ImageIO.read(imagemFile);

            // 🔹 1. Recorte do display (AJUSTE esses valores!)
            BufferedImage display = imagem.getSubimage(
                    300, 120,   // x, y
                    400, 80     // largura, altura
            );

            // 🔹 2. Configuração do Tesseract
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
            tesseract.setLanguage("eng");

            // só números e ponto
            tesseract.setVariable("tessedit_char_whitelist", "0123456789.");

            String texto = tesseract.doOCR(display);

            // 🔹 3. Limpeza do texto
            texto = texto.replaceAll("[^0-9.]", "");

            double vol = Double.parseDouble(texto);

            sujeito.setVolume(vol);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
