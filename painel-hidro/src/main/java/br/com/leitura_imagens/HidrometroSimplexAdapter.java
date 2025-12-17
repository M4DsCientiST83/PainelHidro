package br.com.leitura_imagens;

/**
 * Adapter para hidrômetro simplex com display digital simples.
 * Formato: 6 dígitos pretos uniformes
 * Exemplo: "000009" = 0.009 m³ (últimos 3 dígitos são decimais)
 */
public class HidrometroSimplexAdapter implements HidrometroAdapter {

    @Override
    public int[][] getCropRegions(int width, int height) {
        // Hidrômetro João (Tipo B) - Display está no centro da imagem
        // Imagem tem fundo escuro, display no centro da gota branca
        return new int[][] {
                // Tentar capturar a região central onde estão os números
                // Y: ~38% a 46% da altura
                // X: ~30% a 70% da largura
                { (int) (width * 0.32), (int) (height * 0.38), (int) (width * 0.38), (int) (height * 0.08) },
                { (int) (width * 0.30), (int) (height * 0.37), (int) (width * 0.42), (int) (height * 0.09) },
                { (int) (width * 0.28), (int) (height * 0.39), (int) (width * 0.44), (int) (height * 0.07) },
                { (int) (width * 0.34), (int) (height * 0.38), (int) (width * 0.34), (int) (height * 0.08) }
        };
    }

    @Override
    public double processarVolume(String ocrResult) {
        // Hidrômetro João tem 5 dígitos visíveis: 3 pretos (inteiro) + 2 vermelhos
        // (decimal)
        // Exemplo: "00004" deve ser lido como "000.04" (ou 0.04)
        String volumeStr = ocrResult;

        // Remove caracteres não numéricos exceto ponto
        volumeStr = volumeStr.replaceAll("[^0-9.]", "");

        if (!volumeStr.contains(".")) {
            // Se tem 5 ou mais dígitos, inserir ponto antes dos últimos 2 dígitos
            if (volumeStr.length() >= 5) {
                int posicaoPonto = volumeStr.length() - 2;
                volumeStr = volumeStr.substring(0, posicaoPonto) + "." + volumeStr.substring(posicaoPonto);
            } else if (volumeStr.length() > 2) {
                // Se tem entre 3 e 4 dígitos, inserir ponto antes dos últimos 2
                int posicaoPonto = volumeStr.length() - 2;
                volumeStr = volumeStr.substring(0, posicaoPonto) + "." + volumeStr.substring(posicaoPonto);
            } else {
                // Se tem 1 ou 2 dígitos, são apenas decimais
                // Ex: "4" -> "0.04"
                // Ex: "14" -> "0.14"
                if (volumeStr.length() == 1) {
                    volumeStr = "0.0" + volumeStr;
                } else if (volumeStr.length() == 2) {
                    volumeStr = "0." + volumeStr;
                } else {
                    volumeStr = "0.00"; // Fallback
                }
            }
        }

        try {
            return Double.parseDouble(volumeStr);
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter volume: " + volumeStr);
            return 0.0;
        }
    }

    @Override
    public String getTipo() {
        return "Hidrômetro João (Tipo B)";
    }
}
