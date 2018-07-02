import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { WorkspaceComponent } from './workspace/workspace.component';
import { AgentService } from './services/agent.service';


@NgModule({
  declarations: [
    AppComponent,
    WorkspaceComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [AgentService],
  bootstrap: [AppComponent]
})
export class AppModule { }
