package br.com.estribadobank.banco.Exception;

public class ClienteException extends RuntimeException {
    public ClienteException(String message){
        super(message);
    }

    public static class ClienteNaoCadastradoException extends ClienteException{
        public ClienteNaoCadastradoException(){
            super("Cliente não cadastrado. tente novamente");
        }
    }

    public static class ClienteJaExisteException extends ClienteException{
        public ClienteJaExisteException(){
            super("Cliente ja existe na nossa base. Tente realizar o login");
        }
    }

    public static class LoginIncorretoException extends ClienteException{
        public LoginIncorretoException(){
            super("Login ou Senha incorreta, por favor verifique e tente novamente");
        }
    }

    public static class ClienteNaoEstaLogado extends ClienteException{
        public ClienteNaoEstaLogado(){
            super("Está operação só pode ser realizada se o cliente estiver logado");
        }
    }

    public static class RendaMinimaException extends ClienteException{
        public RendaMinimaException() {super("Cliente não tem renda mínima para upgrade de conta");}
    }
}
