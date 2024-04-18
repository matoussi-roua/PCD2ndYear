export class Token {
    id!: number;
    token!: string;
    tokenType!: TokenType;
    revoked!: boolean;
    expired!: boolean;
}
