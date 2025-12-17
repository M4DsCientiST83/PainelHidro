package br.com.leitura_imagens;

/**
 * Interface Adapter para diferentes tipos de hidrômetros.
 * Cada tipo de hidrômetro tem sua própria estratégia de processamento.
 */
public interface HidrometroAdapter {

    /**
     * Define as regiões de recorte (crop) para o tipo específico de hidrômetro.
     * 
     * @param width  largura da imagem
     * @param height altura da imagem
     * @return array de regiões [x, y, width, height]
     */
    int[][] getCropRegions(int width, int height);

    /**
     * Processa o resultado do OCR e converte para o valor de volume correto.
     * 
     * @param ocrResult texto bruto do OCR (apenas números)
     * @return valor do volume em m³
     */
    double processarVolume(String ocrResult);

    /**
     * Retorna o nome/tipo do hidrômetro.
     * 
     * @return nome do tipo
     */
    String getTipo();
}
