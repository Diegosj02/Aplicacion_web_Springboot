package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Tarjeta;

public interface ITarjetaSservice {

    //Permite buscar una tarjeta dado su ID
    Tarjeta buscarPorId(int id);

    //Permite realizar cambios en una tarjeta (sustraer saldo)
    void guardarTarjeta(Tarjeta tarjeta);

}
