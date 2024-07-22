package br.ufscar.dc.dsw.service.def;

import java.util.List;
import br.ufscar.dc.dsw.domain.FileEntity;

public interface IFileService {
    public FileEntity salvar(FileEntity);

    public void excluir(Long id);

    public FileEntity buscarPorId(Long id);

    public List<FileEntity> buscarTodos();
}