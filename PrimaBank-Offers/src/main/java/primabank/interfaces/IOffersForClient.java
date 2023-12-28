package primabank.interfaces;

import primabank.dto.OfferDTO;

import java.util.List;

public interface IOffersForClient {
    public List<OfferDTO> getAnalysisForClient(Long clientId);
}
