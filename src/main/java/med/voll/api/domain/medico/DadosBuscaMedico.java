package med.voll.api.domain.medico;

public record DadosBuscaMedico(Long id, String nome, String email, String crm, Especialidade especialidade) {

    public DadosBuscaMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }

}
