package TFG.Front.tfgfront.services;

import TFG.Front.tfgfront.model.Usuario;
import TFG.Front.tfgfront.model.Torneo;
import TFG.Front.tfgfront.model.UsuarioParticipaTorneo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsuarioParticipaTorneo {

    //Permite buscar todos los usuarios que participan en un torneo
    Page<UsuarioParticipaTorneo> buscarPorTorneo(Torneo torneo, Pageable pageable);

    //Permite registrar un usuario en un torneo
    String registrarUsuarioEnTorneo(UsuarioParticipaTorneo usuarioParticipaTorneo);
}
