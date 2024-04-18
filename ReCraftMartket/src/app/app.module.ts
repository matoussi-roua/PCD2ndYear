import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { ProfileComponent } from './profile/profile.component';
import { LeaderboardComponent } from './leaderboard/leaderboard.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { CardproductComponent } from './cardproduct/cardproduct.component';
import { ChatComponent } from './chat/chat.component';
import { NotfoundComponent } from './notfound/notfound.component';
import { TopcustomerComponent } from './topcustomer/topcustomer.component';
import { DetailsComponent } from './details/details.component';
import { CommentsComponent } from './comments/comments.component';
import { LogComponent } from './login/log.component';
import { AddproductComponent } from './addproduct/addproduct.component';
//import { ModelsComponent } from './models/models.component';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
/*login */
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import{MatInputModule} from '@angular/material/input';
import{ReactiveFormsModule} from '@angular/forms';
import { UpdateUserComponent } from './update-user/update-user.component';
import { UpdateComponent } from './update/update.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

import { HomeComponent } from './home/home.component';
import { Navbar2Component } from './navbar2/navbar2.component';
import { AboutComponent } from './about/about.component';
import { FooterComponent } from './footer/footer.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'; //i added httpinterceptors and httpclient 
import { AuthInterceptor } from './interceptors/auth-interceptor';

/*login */
@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SidebarComponent,
    ProfileComponent,
    LeaderboardComponent,
    DashboardComponent,
    CardproductComponent,
    ChatComponent,
    NotfoundComponent,
    TopcustomerComponent,
    DetailsComponent,
    CommentsComponent,
    LogComponent,
    AddproductComponent,
    UpdateUserComponent,
    UpdateComponent,
    HomeComponent,
    Navbar2Component,
    AboutComponent,
    FooterComponent
   // ModelsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
     MatCardModule,
     MatButtonModule,
     MatInputModule,
     ReactiveFormsModule,
      MatDialogModule,
     MatIconModule,
     HttpClientModule //i added httpclientmodule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true // Set to true to allow multiple interceptors
    },
    provideClientHydration(),
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
