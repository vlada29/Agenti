import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RestServiceService } from './rest-service.service';
import { HttpClientModule  } from '@angular/common/http';
import { AppComponent } from './app.component';



@NgModule({
  declarations: [
    AppComponent ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [RestServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
