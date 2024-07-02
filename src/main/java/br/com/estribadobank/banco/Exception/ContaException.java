package br.com.estribadobank.banco.Exception;

public class ContaException extends RuntimeException {

    public ContaException(String message){
        super(message);
    }

    public static class ContaNaoExisteException extends ContaException{
        public ContaNaoExisteException(){
            super("Conta não cadastrada. Peça para o cliente verificar as informações");
        }
    }

    public static class SemLimiteException extends ContaException{
        public SemLimiteException(){
            super("Limite insuficiente para fazer a transação");
        }
    }

    public static class ChavePixInvalidaException extends ContaException{
        public ChavePixInvalidaException(){
            super("Chave pix inválida");
        }
    }

    public static class ContaSemPermissaoException extends ContaException{
        public ContaSemPermissaoException(){
            super("O cliente não tem nivel suficente pra realizar esta ação");
        }
    }

}
