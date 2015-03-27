package com.example.android.androidwearjderobot;

/**
 * Created by MAC on 27/03/15.
 */
import jderobot.AMD_ImageProvider_getImageData;
import jderobot.DataNotExistException;
import jderobot.HardwareFailedException;
import jderobot.ImageData;
import jderobot.ImageDescription;
import jderobot.Time;
import Ice.Current;

public class ImageProviderI extends jderobot._ImageProviderDisp {

    /**
     * La descripciÃ³n de la imagen que serÃ¡ entregada en
     * <code>getImageDescription</code>.
     *
     * @see #getImageDescription()
     */
    public static ImageDescription idDatos;

    /**
     * Contenido de la imagen (incluyendo descripciÃ³n y fecha) que serÃ¡ entregado en
     * <code>getImageData</code>.
     *
     * @see #getImageData()
     */
    public static ImageData idImagen;

    /**
     * NÃºmero de serie generado.
     */
    private static final long serialVersionUID = 1L;

    public ImageProviderI() {
		/* Inicializamos la descripciÃ³n de la imagen */
        idDatos = new ImageDescription();
        idDatos.height = 0;
        idDatos.width = 0;
        idDatos.format = "NONE";
		/* Inicializamos los datos de la imagen */
        idImagen = new ImageData();
        idImagen.pixelData = new byte[0];
        idImagen.description = idDatos;
        idImagen.timeStamp = new Time();
        idImagen.timeStamp.seconds = 0;
        idImagen.timeStamp.useconds = 0;
    }

    /**
     * Entrega la descripciÃ³n de la imagen.
     * @return Datos de la descripciÃ³n de la imagen
     */
    @Override
    public ImageDescription getImageDescription(Current __current) {
        return idDatos;
    }

    static public java.util.LinkedList<Job> _jobs = new java.util.LinkedList<Job>();
    /**
     * Entrega la imagen y su descripciÃ³n asociada.
     */
    @Override
    public void getImageData_async(AMD_ImageProvider_getImageData __cb,
                                   Current __current) throws DataNotExistException,
            HardwareFailedException {
        _jobs.add(new Job(__cb));
    }

    class Job {
        Job(AMD_ImageProvider_getImageData __cb)
        {
            cb = __cb;
        }

        void execute()
        {
            cb.ice_response(idImagen);
        }


        private AMD_ImageProvider_getImageData cb;
    }
}