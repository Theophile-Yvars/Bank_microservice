package al.newbank.d.Marketing.interfaces;

import al.newbank.d.Marketing.dto.AnalysisDTO;

public interface IProduceEvent {
    void send(AnalysisDTO analysisDTO);
}
