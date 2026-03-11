package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping("/criar")
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> cadastrarMedico(@RequestBody @Valid DadosCadastroMedico request, UriComponentsBuilder uriBuilder) {
        var medico = new Medico(request);
        medicoRepository.save(medico);

        var uri = uriBuilder.path("/medicos/get/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping("/get")
    public ResponseEntity<Page<DadosBuscaMedico>> getMedico(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = medicoRepository.findAllByAtivoTrue(paginacao).map(DadosBuscaMedico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DadosDetalhamentoMedico> DadosBuscaMedicosId(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);

        if (!medico.getAtivo()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @PutMapping("/atualizar")
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> atualizarMedico(@RequestBody @Valid DadosAtualizarMedico request) {
        var medico = medicoRepository.getReferenceById(request.id());
        medico.atualizarInformacoes(request);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/excluir/{id}")
    @Transactional
    public ResponseEntity<Void> excluirMedico(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }
}
