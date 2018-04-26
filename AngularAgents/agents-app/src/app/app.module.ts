import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RestServiceService } from './rest-service.service';
import { HttpClientModule  } from '@angular/common/http';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UserprofileComponent } from './userprofile/userprofile.component';
import { ChatroomComponent } from './chatroom/chatroom.component';
import { AppRoutingModule } from './/app-routing.module';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    UserprofileComponent,
    ChatroomComponent ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [RestServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
