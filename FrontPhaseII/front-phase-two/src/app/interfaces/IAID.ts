import { IAgentskiCentar } from "./IAgentskiCentar";
import { IAgentType } from "./IAgentType";

export interface IAID {
    name: string;
    host: IAgentskiCentar;
    type: IAgentType;
}