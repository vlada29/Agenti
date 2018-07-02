import { Performative } from "./Performative";
import { IAID } from "./IAID";

export interface IACLMessage {
    performative: Performative;
    sender: IAID;
    receivers: IAID[];
    replyTo: IAID;
    content: string;
    contentObj: object;
    userArgs: Map<string,object>;
    language: string;
    encoding: string;
    ontology: string;
    protocol: string;
    conversationID: string;
    replyWith: string;
    inReplyTo: string;
    replyBy: string;
}
