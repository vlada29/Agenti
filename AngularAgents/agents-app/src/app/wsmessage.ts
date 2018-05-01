export class WSMessage {
    private toService: string;
    private content: string;
    private sender: string;
    constructor(t, c, s) {
        this.toService = t;
        this.content = c;
        this.sender = s;
    }
}
