package src.br.com.aula.util;

import java.sql.SQLException;

public class TratamentoErro extends Exception {

    public TratamentoErro(String mensagem) {
        super(mensagem);
    }

    public TratamentoErro(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
