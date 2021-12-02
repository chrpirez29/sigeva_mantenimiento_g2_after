export class LoginUsuario {
  username: string;
  hashPassword: string;

  constructor(username: string, hashPassword: string) {
    this.username = username;
    this.hashPassword = hashPassword;
  }
}
