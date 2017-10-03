import java.util.Arrays;

/**
 * Clase para el manejo de las estadisticas.
 */
class Statistics
{
    private double[] data;
    private int size;

    /**
     * Constructor del cual se obtienen distintas estadisticas.
     * @param data Arreglo con los datos que se usaran para calcularlas.
     */
    Statistics(double[] data)
    {
        this.data = data;
        size = data.length;
    }

    /**
     * Calcula el promedio de los datos.
     * @return Promedio de los datos.
     */
    double getMean()
    {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return sum/size;
    }

    /**
     * Calcula la varianza de los datos.
     * @return Varianza de los datos.
     */
    double getVariance()
    {
        double mean = getMean();
        double temp = 0;
        for(double a :data)
            temp += (a-mean)*(a-mean);
        return temp/(size-1);
    }

    /**
     * Calcula la desviacion estandar de los datos.
     * @return Desviacion estandar de los datos.
     */
    double getStdDev()
    {
        return Math.sqrt(getVariance());
    }

    /**
     * Calcula la media de los datos.
     * @return Media de los datos.
     */
    double median()
    {
        Arrays.sort(data);

        if (data.length % 2 == 0)
        {
            return (data[(data.length / 2) - 1] + data[data.length / 2]) / 2.0;
        }
        return data[data.length / 2];
    }
}

