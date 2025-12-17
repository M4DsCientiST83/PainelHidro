package br.com.leitura_imagens;

/**
 * Adapter para hidrômetro digital com display colorido.
 * Formato: 4 dígitos pretos (inteiro) + 2 dígitos vermelhos (decimal)
 * Exemplo: "000173" = 0.00173 m³
 */
public class HidrometroDigitalAdapter implements HidrometroAdapter {

    @Override
    public int[][] getCropRegions(int width, int height) {
        // Display digital está no centro-superior (36-42% vertical)
        return new int[][] {
                { (int) (width * 0.30), (int) (height * 0.36), (int) (width * 0.40), (int) (height * 0.06) }, // Display
                                                                                                              // principal
                { (int) (width * 0.28), (int) (height * 0.35), (int) (width * 0.44), (int) (height * 0.07) }, // Um
                                                                                                              // pouco
                                                                                                              // mais
                                                                                                              // largo
                { (int) (width * 0.25), (int) (height * 0.34), (int) (width * 0.50), (int) (height * 0.08) }, // Ainda
                                                                                                              // mais
                                                                                                              // largo
                { (int) (width * 0.32), (int) (height * 0.37), (int) (width * 0.36), (int) (height * 0.05) } // Mais
                                                                                                             // estreito
        };
    }

    @Override
    public double processarVolume(String ocrResult) {
        // O hidrômetro tem formato: 4 dígitos pretos (inteiro) + 2 dígitos vermelhos
        // (decimal)
        // Exemplo: "000173" deve ser lido como "0.00173"
        String volumeStr = ocrResult;

        if (!volumeStr.contains(".")) {
            // Se tem 6 ou mais dígitos, inserir ponto antes dos últimos 2 dígitos
            if (volumeStr.length() >= 6) {
                int posicaoPonto = volumeStr.length() - 2;
                volumeStr = volumeStr.substring(0, posicaoPonto) + "." + volumeStr.substring(posicaoPonto);
            } else if (volumeStr.length() > 2) {
                // Se tem menos de 6 dígitos mas mais de 2, ainda inserir ponto antes dos
                // últimos 2
                int posicaoPonto = volumeStr.length() - 2;
                volumeStr = volumeStr.substring(0, posicaoPonto) + "." + volumeStr.substring(posicaoPonto);
            } else {
                // Se tem 2 ou menos dígitos, são apenas decimais
                volumeStr = "0." + volumeStr;
            }
        }

        return Double.parseDouble(volumeStr);
    }

    @Override
    public String getTipo() {
        return "Digital Colorido (4+2 dígitos)";
    }
}
