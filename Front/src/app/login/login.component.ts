import { Component } from '@angular/core';
import axios from 'axios';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  urlservices: any = "http://192.168.1.39:8080/"


  bloquearXSS = (cadena: any) => {
    let resultado = cadena.replace(/</g, "&lt;").replace(/>/g, "&gt;");
    return resultado;
  }

  encryptData(data: any): string {
    const jsonString = JSON.stringify(data);
    const encodedString = btoa(jsonString);
    return encodedString;
  }


  login() {


    let email = (<HTMLInputElement>document.getElementById('email')).value;
    let password = (<HTMLInputElement>document.getElementById('password')).value;
    let url = this.urlservices + "Login";

    if (email == "" || password == "") {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'No puede dejar campos vacios',
        confirmButtonColor: '#b20303',
      })
      return;

    }

    let email2 = this.bloquearXSS(email);
    let password2 = this.bloquearXSS(password);


    let data = {
      usuario: email2,
      contrasenia: password2

    }



    axios.post(url, data).then(
      response => {
        if (response.data == "" || response.data == null) {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Usuario o contraseña incorrectos',
            confirmButtonColor: '#b20303',
          })
          return;
        }

        localStorage.setItem("usuario", this.encryptData(response.data));
        window.location.href = "/feed";

      }
    ).catch(
      error => {
        console.log(error);
      }
    )



  }




}
