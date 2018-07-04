import { Component, OnInit } from '@angular/core';
import { AgentService } from '../services/agent.service';
import { IAgent } from '../interfaces/IAgent';
import { IACLMessage } from '../interfaces/IACLMessage';
import { IAID } from '../interfaces/IAID';
import { Performative } from '../interfaces/Performative';

@Component({
  selector: 'app-workspace',
  templateUrl: './workspace.component.html',
  styleUrls: ['./workspace.component.css']
})
export class WorkspaceComponent implements OnInit {

  private allAgentTypes;
  private allRunningAgents;
  public selektovanAgentTip:IAgent = {id: {name:'',host:{address: '',alias: ''},type:{module:'',name:''}}};
  public selektovanPokrenutAgent:IAgent = {id: {name:'',host:{address: '',alias: ''},type:{module:'',name:''}}};

  public performativeLabel = "Select a performative";

  private sender:IAgent;
  private replyTo:IAgent;
  private recievers:IAID[] = [];
  
  private primljenaPoruka;
  private ws;

  constructor(private agent_service: AgentService) { }

  ngOnInit() {
    this.agent_service.getAgentTypes().subscribe(data => this.allAgentTypes = data);
    this.agent_service.getRunningAgents().subscribe(data => this.allRunningAgents = data);
    this.ws = new WebSocket('ws://localhost:8080/SecondPhase/logger/vlada');
    this.ws.onopen = () => this.ws.send('ok');
    this.ws.onmessage = (event) => {
		this.primljenaPoruka = event.data;
        console.log(this.primljenaPoruka);

		document.getElementById('messagesDiv').innerHTML += '<div>' + this.primljenaPoruka + '</div>';
    
    }
  }

  setSelektovanAgentTip(a){
    this.selektovanAgentTip = a;
  }

  setSelektovanPokrenutAgent(a){
    this.selektovanPokrenutAgent = a;
  }

  pokreniAgenta(ime){
    this.agent_service.pokreniAgenta(this.selektovanAgentTip.id.type,ime).subscribe(
      data => { this.agent_service.getRunningAgents().subscribe(data => this.allRunningAgents = data); },
      error => { alert("Greska"); }
    );
  }

  zaustaviAgenta(){
    this.agent_service.zaustaviAgenta(this.selektovanPokrenutAgent.id.name).subscribe(
      data => { this.agent_service.getRunningAgents().subscribe(data => this.allRunningAgents = data); },
      error => { alert("Greska"); }
    );
  }

  posaljiPoruku(content,language,encoding,ontology,protocol,conversationId,replyWith,inReplyTo,replyBy){
    var poruka: IACLMessage = {
      performative: Performative[this.performativeLabel],
      sender: this.sender.id,
      receivers: this.recievers,
      replyTo: this.replyTo.id,
      content: content,
      contentObj: {},
      userArgs: new Map<string,object>(),
      language: language,
      encoding: encoding,
      ontology: ontology,
      protocol: protocol,
      conversationID: conversationId,
      replyWith: replyWith,
      inReplyTo: inReplyTo,
      replyBy: replyBy
    }

    this.agent_service.posaljiPoruku(poruka);
  }

  setSender(a){
    this.sender = a;
  }

  setReplyTo(a){
    this.replyTo = a;
  }

  addReciever(a){
    this.recievers.push(a);
  }

  setPerformative(text,num){
    this.performativeLabel = text;
  }
}
