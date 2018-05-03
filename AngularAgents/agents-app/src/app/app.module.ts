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
import { HeaderComponent } from './header/header.component';
import { LogoutComponent } from './logout/logout.component';
import { AuthGuard} from './auth.guard';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    UserprofileComponent,
    ChatroomComponent ,
    HeaderComponent,
    LogoutComponent ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [RestServiceService, UserServiceService, SocketsServiceService, AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
