package se.ltucoders.bothniabladetdam.service;


import edu.harvard.hul.ois.fits.Fits;
import edu.harvard.hul.ois.fits.FitsMetadataElement;
import edu.harvard.hul.ois.fits.FitsOutput;
import edu.harvard.hul.ois.fits.exceptions.FitsException;
import edu.harvard.hul.ois.fits.identity.ExternalIdentifier;
import edu.harvard.hul.ois.fits.identity.FitsIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ltucoders.bothniabladetdam.db.entity.Image;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MetadataService {

    private Fits fits;
    DataParsingService dataParsingService;
    GpsLocationService gpsLocationService;

    @Autowired
    public MetadataService(Fits fits, DataParsingService dataParsingService, GpsLocationService gpsLocationService) {
            this.fits = fits;
            this.dataParsingService = dataParsingService;
            this.gpsLocationService = gpsLocationService;
    }

    protected void extractMetadata(Image image, File theFile) {
        FitsOutput fitsOutput;
        try {
            fitsOutput = fits.examine(theFile);
        } catch (FitsException e) {
            e.printStackTrace();
            return;
        }

        extractResolution(image, fitsOutput);

        extractWidth(image, fitsOutput);

        extractHeight(image, fitsOutput);

        extractFileSize(image, fitsOutput);

        extractDateTime(image, fitsOutput);

        extractMake(image, fitsOutput);

        extractModel(image, fitsOutput);

        extractLocation(image, fitsOutput);

        extractPuid(image, fitsOutput);
    }

    private void extractPuid(Image image, FitsOutput fitsOutput) {
        for (FitsIdentity fitsIdentity : fitsOutput.getIdentities()) {
            for (ExternalIdentifier externalIdentifier : fitsIdentity.getExternalIdentifiers()) {
                if (externalIdentifier.getName().equals("puid")) {
                    image.setPuid(externalIdentifier.getValue());
                    return;
                }
            }
        }
        image.setPuid("Pronom is not defined.");
    }

    private void extractLocation(Image image, FitsOutput fitsOutput) {
        FitsMetadataElement element;
        element = fitsOutput.getMetadataElement("gpsLongitude");
        String gpsLongitude = element != null ? element.getValue() : "";
        element = fitsOutput.getMetadataElement("gpsLatitude");
        String gpsLatitude = element != null ? element.getValue() : "";
        image.setLocation(gpsLocationService.getLocation(gpsLatitude.replace("+", ""),
                gpsLongitude.replace("+", "")));
    }

    private void extractModel(Image image, FitsOutput fitsOutput) {
        FitsMetadataElement element;
        element = fitsOutput.getMetadataElement("digitalCameraModelName");
        image.setModel(element != null ? element.getValue() : "Camera model is not defined.");
    }

    private void extractMake(Image image, FitsOutput fitsOutput) {
        FitsMetadataElement element;
        element = fitsOutput.getMetadataElement("digitalCameraManufacturer");
        image.setMake( element != null ? element.getValue() : "Camera manufacturer is not defined.");
    }

    private void extractDateTime(Image image, FitsOutput fitsOutput) {
        FitsMetadataElement element;
        element = fitsOutput.getMetadataElement("created");
        image.setDateTime(element != null
                ? dataParsingService.parseDateTime(element.getValue(), "yyyy:MM:dd HH:mm:ss")
                : LocalDateTime.of(1970, 1,1,0,0));
    }

    private void extractFileSize(Image image, FitsOutput fitsOutput) {
        FitsMetadataElement element;
        element = fitsOutput.getMetadataElement("size");
        image.setFileSize(element != null ? element.getValue() : "Size is not defined.");
    }

    private void extractHeight(Image image, FitsOutput fitsOutput) {
        FitsMetadataElement element;
        element = fitsOutput.getMetadataElement("imageHeight");
        image.setHeight(element != null ? Integer.parseInt(element.getValue()) : 0);
    }

    private void extractWidth(Image image, FitsOutput fitsOutput) {
        FitsMetadataElement element;
        element = fitsOutput.getMetadataElement("imageWidth");
        image.setWidth(element != null ? Integer.parseInt(element.getValue()) : 0);
    }

    private void extractResolution(Image image, FitsOutput fitsOutput) {
        FitsMetadataElement element = fitsOutput.getMetadataElement("xSamplingFrequency");
        image.setResolution(element != null ? element.getValue() + " dpi" : "");
    }
























//
//    public String extractFileName(File file) {
//        try {
//            FitsOutput fitsOutput = fits.examine(file);
//            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("filename");
//            if (fitsMetadataElement != null) {
//                return fitsMetadataElement.getValue();
//            }
//        } catch (FitsException ex) {
//            ex.printStackTrace();
//        }
//        return "File name is not defined";
//    }
//
//
//    public String extractResolution(File file) {
//        try {
//            FitsOutput fitsOutput = fits.examine(file);
//            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("xSamplingFrequency");
//            if (fitsMetadataElement != null) {
//                return fitsMetadataElement.getValue() + " dpi";
//            }
//        } catch (FitsException ex) {
//            ex.printStackTrace();
//        }
//        return "Resolution is not defined";
//    }
//
//
//    public int extractWidth(File file) {
//        try {
//            FitsOutput fitsOutput = fits.examine(file);
//            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("imageWidth");
//            if (fitsMetadataElement != null) {
//                return Integer.parseInt(fitsMetadataElement.getValue());
//            }
//        } catch (FitsException ex) {
//            ex.printStackTrace();
//        }
//        return 0;
//    }
//
//
//    public int extractHeight(File file) {
//        try {
//            FitsOutput fitsOutput = fits.examine(file);
//            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("imageHeight");
//            if (fitsMetadataElement != null) {
//                return Integer.parseInt(fitsMetadataElement.getValue());
//            }
//        } catch (FitsException ex) {
//            ex.printStackTrace();
//        }
//        return 0;
//    }
//
//
//    public String extractSize(File file) {
//        try {
//            FitsOutput fitsOutput = fits.examine(file);
//            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("size");
//            if (fitsMetadataElement != null) {
//                return fitsMetadataElement.getValue();
//            }
//        } catch (FitsException ex) {
//            ex.printStackTrace();
//        }
//        return "Size is not defined.";
//    }
//
//
//    // TODO: Should it return now() or null?
//    public LocalDateTime extractDateTime(File file) {
//        try {
//            FitsOutput fitsOutput = fits.examine(file);
//
//            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("created");
//            if (fitsMetadataElement != null) {
//                return dataParsingService.parseDateTime(fitsMetadataElement.getValue(),
//                        "yyyy:MM:dd HH:mm:ss");
//
//            }
//        } catch (FitsException ex) {
//            ex.printStackTrace();
//        }
//        return LocalDateTime.now();
//    }
//
//
//    public String extractCameraManufacturer(File file) {
//        try {
//            FitsOutput fitsOutput = fits.examine(file);
//            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("digitalCameraManufacturer");
//            if (fitsMetadataElement != null) {
//                return fitsMetadataElement.getValue();
//            }
//        } catch (FitsException ex) {
//            ex.printStackTrace();
//        }
//        return "Camera manufacturer is not defined.";
//    }
//
//
//    //digitalCameraModelName
//    public String extractCameraModelName(File file) {
//        try {
//            FitsOutput fitsOutput = fits.examine(file);
//            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("digitalCameraModelName");
//            if (fitsMetadataElement != null) {
//                return fitsMetadataElement.getValue();
//            }
//        } catch (FitsException ex) {
//            ex.printStackTrace();
//        }
//        return "Camera model is not defined.";
//    }
//
//
//    public String extractProducer(File file) {
//        try {
//            FitsOutput fitsOutput = fits.examine(file);
//            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("imageProducer");
//            if (fitsMetadataElement != null) {
//                return fitsMetadataElement.getValue();
//            }
//        } catch (FitsException ex) {
//            ex.printStackTrace();
//        }
//
//        return "Image producer is not defined.";
//    }
//
//
////    public String extractLocation(File file) {
////        try {
////            FitsOutput fitsOutput = fits.examine(file);
////            FitsMetadataElement gpsLatitude = fitsOutput.getMetadataElement("gpsLatitude");
////            FitsMetadataElement gpsLongitude = fitsOutput.getMetadataElement("gpsLongitude");
////            if (gpsLatitude != null && gpsLongitude != null) {
////                String gpsLocation = gpsLatitude.getValue() + " " + gpsLongitude.getValue();
////                return gpsLocation;
////            }
////        } catch (FitsException ex) {
////            ex.printStackTrace();
////        }
////        return "GPS location is not defined.";
////    }
//
//
//    public String extractGpsLongitude(File file) {
//        try {
//            FitsOutput fitsOutput = fits.examine(file);
//            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("gpsLongitude");
//            if (fitsMetadataElement != null) {
//                String gpsLongitude = fitsMetadataElement.getValue();
//                return gpsLongitude.replace("+", "");
//            }
//        } catch (FitsException ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//
//    public String extractGpsLatitude(File file) {
//        try {
//            FitsOutput fitsOutput = fits.examine(file);
//            FitsMetadataElement fitsMetadataElement = fitsOutput.getMetadataElement("gpsLatitude");
//            if (fitsMetadataElement != null) {
//                String gpsLatitude = fitsMetadataElement.getValue();
//                return gpsLatitude.replace("+", "");
//            }
//        } catch (FitsException ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//
//    public String extractLocation(File file) {
//        String gpsLongitude = extractGpsLongitude(file);
//        String gpsLatitude = extractGpsLatitude(file);
//        if (gpsLongitude != null && gpsLatitude != null) {
//            return gpsLocationService.getLocation(gpsLatitude, gpsLongitude);
//        }
//        return "Not defined";
//    }
//
//
//    public String extrcatPronom(File file) {
//        try {
//            FitsOutput fitsOutput = fits.examine(file);
//            List<FitsIdentity> fitsIdentityList = fitsOutput.getIdentities();
//            for (FitsIdentity fitsIdentity : fitsIdentityList) {
//                List<ExternalIdentifier> externalIdentifierList = fitsIdentity.getExternalIdentifiers();
//                for (ExternalIdentifier externalIdentifier : externalIdentifierList) {
//                    if (externalIdentifier.getName().equals("puid")) {
//                        return externalIdentifier.getValue();
//                    }
//                }
//            }
//        } catch (FitsException ex) {
//            ex.printStackTrace();
//        }
//        return "Pronom is not defined.";
//    }
//

} // End class
