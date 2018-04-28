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
import { UserServiceService } from './user-service.service';
import { SocketsServiceService } from './sockets-service.service';



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
  providers: [RestServiceService, UserServiceService, SocketsServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
