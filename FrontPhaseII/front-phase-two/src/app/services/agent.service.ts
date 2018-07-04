import { Injectable } from '@angular/core';
import { IAgent } from "../interfaces/IAgent";
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class AgentService {

  constructor(private http: HttpClient) { }

  public prefiks = "SecondPhase/rest";

  public getAgentsTypes(){
    var agents: IAgent[];
    agents = [
      {id: {name:'Agent1',host:{address: 'localhost:8080',alias: 'master'},type:{module:'module1',name:'agentTypeName1'}}},
      {id: {name:'Agent1',host:{address: 'localhost:8080',alias: 'master'},type:{module:'module1',name:'agentTypeName1'}}}
    ];

    return agents;
  }

  public getAgentTypes(): Observable<IAgent[]>{
    return this.http.get<IAgent[]>(this.prefiks+'/agents/classes');
  }

  public getRunningAgents(): Observable<IAgent[]>{
    return this.http.get<IAgent[]>(this.prefiks+'/agents/running');
  }

  public pokreniAgenta(type,name):Observable<any>{
    return this.http.put(this.prefiks+'/agents/running/'+type+'/'+name,{});
  }

  public zaustaviAgenta(aid): Observable<any>{
    return this.http.delete(this.prefiks+'/agents/running/'+aid);
  }

  public posaljiPoruku(message): Observable<any>{
    return this.http.post(this.prefiks+'/message/messages',message);
  }
  
}
