import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { Routes, RouterModule } from '@angular/router';
import { ChatroomComponent } from './chatroom/chatroom.component';
import { UserprofileComponent } from './userprofile/userprofile.component';
import { LogoutComponent } from './logout/logout.component';
import { AuthGuard} from './auth.guard';

const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
    },
    {
      path: 'register',
      component: RegisterComponent
  },

  {
    path: 'chatroom',
    component: ChatroomComponent,
    canActivate: [AuthGuard]
},
{
  path: 'userprofile',
  component: UserprofileComponent,
  canActivate: [AuthGuard]
},
 {
   path: 'logout',
   component: LogoutComponent
}
];
@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule],
  declarations: []
})
export class AppRoutingModule { }
