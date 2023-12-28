package primabank.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import primabank.dto.OfferDTO;
import primabank.entities.Offer;
import primabank.interfaces.IOffers;
import primabank.interfaces.IOffersForClient;
import primabank.repositories.OffersRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class OffersService implements IOffersForClient, IOffers {
    OffersRepository offersRepository;

    @Autowired
    public OffersService(OffersRepository offersRepository) {
        this.offersRepository = offersRepository;
    }

    public List<OfferDTO> getAnalysisForClient(Long clientId) {
        // use it to match client id
        List<Offer> offers = offersRepository.findByIdClient(clientId);
        List<OfferDTO> offersDTOList = new ArrayList<>();

        for(Offer offer : offers){
            offersDTOList.add(this.toDTO(offer));
        }

        return offersDTOList;
        
    }

    private OfferDTO toDTO(Offer offer) {
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setIdClient(offer.getIdClient());
        offerDTO.setProductId(offer.getProductId());
        offerDTO.setProductName(offer.getProductName());
        offerDTO.setProductDescription(offer.getProductDescription());
        offerDTO.setProductPrice(offer.getProductPrice());
        offerDTO.setProductMode(offer.getProductMode());
        return offerDTO;
    }

    @Override
    public boolean delete() {
        this.offersRepository.deleteAll();
        return true;
    }
}