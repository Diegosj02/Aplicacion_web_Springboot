package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Torneo;

public interface ITorneoService {

    //Busca un Torneo dado su ID
    Torneo buscarPorId(Integer id);

    //Permite añadir o actualizar torneos
    void guardarTorneo(Torneo torneo);

}
