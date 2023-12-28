package primabank.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import primabank.dto.OfferDTO;
import primabank.entities.Offer;
import primabank.interfaces.IOffers;
import primabank.interfaces.IOffersForClient;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = OffersController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class OffersController {
    public static final String BASE_URI = "/offers";

    private Logger logger = LoggerFactory.getLogger(OffersController.class);

    private IOffersForClient offersForClient;

    private IOffers offers;

    @Autowired
    public OffersController(IOffersForClient offersForClient, IOffers offers) {
        this.offersForClient = offersForClient;
        this.offers = offers;
    }

    @GetMapping("/getOffers/{clientId}")
    public ResponseEntity<List<OfferDTO>>  getOffers(@PathVariable Long clientId){
        logger.info("");
        logger.info("==================================================================");
        logger.info("");
        logger.info("Demande pouur : " + clientId);
        List<OfferDTO> offers = this.offersForClient.getAnalysisForClient(clientId);
        logger.info("Offers : " + offers);
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/delete")
    public ResponseEntity<Boolean> deleteOffers(){
        logger.info("");
        logger.info("==================================================================");
        logger.info("");
        logger.info("DELETE ...");
        boolean rep = this.offers.delete();
        return ResponseEntity.ok(rep);
    }
}
