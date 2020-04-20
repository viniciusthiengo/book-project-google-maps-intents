package thiengo.com.br.googlemapsintents

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_login.*

/*
 * Atividade criada somente para teste do método
 * intencoesExplicitasImplicitIntent_Algoritmo_2()
 * do tópico "Intenções explícitas (explicit intent)".
 * */
class LoginActivity : AppCompatActivity() {

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_login )
        setSupportActionBar( toolbar )
        supportActionBar?.setDisplayHomeAsUpEnabled( true )
    }
}
