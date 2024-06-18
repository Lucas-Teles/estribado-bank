package br.com.estribadobank.banco.Exception;

public class ContaException extends RuntimeException {

    public ContaException(String message){
        super(message);
    }

    public static class SemLimiteException extends ContaException{
        public SemLimiteException(){
            super("Limite insuficiente para fazer a transação");
        }
    }

    public static class SemChavePixCadastradaException extends ContaException{
        public SemChavePixCadastradaException(){
            super("O Cliente ainda não tem chave pix");
        }
    }

    public static class ContaSemPermissaoException extends ContaException{
        public ContaSemPermissaoException(){
            super("O cliente não tem nivel suficente pra realizar esta ação");
        }
    }

}
