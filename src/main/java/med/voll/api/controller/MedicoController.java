package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.DadosAtualizarMedico;
import med.voll.api.medico.DadosBuscaMedico;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping("/criar")
    @Transactional
    public void cadastrarMedico(@RequestBody @Valid DadosCadastroMedico request) {
        medicoRepository.save(new Medico(request));
    }

    @GetMapping("/get")
    public Page<DadosBuscaMedico> getMedico(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return medicoRepository.findAllByAtivoTrue(paginacao).map(DadosBuscaMedico::new);
    }

    @PutMapping("/atualizar")
    @Transactional
    public void atualizarMedico(@RequestBody @Valid DadosAtualizarMedico request) {
        var medico = medicoRepository.getReferenceById(request.id());
        medico.atualizarInformacoes(request);
    }

    @DeleteMapping("/excluir/{id}")
    @Transactional
    public void excluirMedico(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.excluir();
    }
}
