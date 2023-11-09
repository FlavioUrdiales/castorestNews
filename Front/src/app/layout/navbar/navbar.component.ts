import { Component } from '@angular/core';
import { FeedComponent } from 'src/app/feed/feed.component';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent extends FeedComponent {

  constructor() {
    super();
  }

  



  singOut = () => {
      
      localStorage.removeItem("usuario");
      window.location.href = "/login";
  
    }







}
