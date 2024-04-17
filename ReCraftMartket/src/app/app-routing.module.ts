import { AddproductComponent } from './addproduct/addproduct.component';
import { CardproductComponent } from './cardproduct/cardproduct.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavbarComponent } from './navbar/navbar.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { ProfileComponent } from './profile/profile.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ChatComponent } from './chat/chat.component';

//import { NotfoundComponent } from './notfound/notfound.component';

//import path from 'path';
import { LeaderboardComponent } from './leaderboard/leaderboard.component';
import { TopcustomerComponent } from './topcustomer/topcustomer.component';
import { DetailsComponent } from './details/details.component';
import { CommentsComponent } from './comments/comments.component';
import { LogComponent } from './login/log.component';
import { UpdateUserComponent } from './update-user/update-user.component';
import { UpdateComponent } from './update/update.component';
import { HomeComponent } from './home/home.component';
import { Navbar2Component } from './navbar2/navbar2.component';
import { FooterComponent } from './footer/footer.component';


const routes: Routes = [
 //   {path:"", redirectTo:"/navbar",pathMatch :'full'},

  //{path:"", redirectTo:"/cardproduct",pathMatch :'full'},
{path:"navbar2",component:Navbar2Component},
{path:"footer",component:FooterComponent},

{path:"navbar",component:NavbarComponent},
  {path:"sidebar",component:SidebarComponent},
  {path:"profile",component:ProfileComponent},
  {path:"dashboard",component:DashboardComponent},
  {path:"chat",component:ChatComponent},
  {path:"cardproduct",component:CardproductComponent},
   {path:"updateuser",component:UpdateUserComponent},
   {path:"leaderboard",component:LeaderboardComponent},
   {path:"topcustomer",component:TopcustomerComponent},
   {path:"details",component:DetailsComponent},
   {path:"comments",component:CommentsComponent},
   {path:"log",component:LogComponent},
    {path:"update",component:UpdateComponent},
    {path:"home",component:HomeComponent},
   {path:"addproduct",component:AddproductComponent}
  // {path:"**",component:NotfoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
