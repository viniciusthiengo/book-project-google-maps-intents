package thiengo.com.br.googlemapsintents

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnScrollChangedListener
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_section_2.*
import kotlinx.android.synthetic.main.content_section_3.*


class MainActivity : AppCompatActivity(), OnScrollChangedListener {

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main )
        sv_root.viewTreeObserver.addOnScrollChangedListener( this )

        /*
         * Tópico ➙ Evitando exceções quando a intenção não puder ser respondida
         *
         * A intenção já configurada é entregue a variável intent, via
         * getMapIntent(), ainda no onCreate() da atividade que contém
         * a funcionalidade de acionamento de mapa. Dessa forma é
         * possível esconder ou não o botão que permite o acionamento
         * do mapa no Google Maps.
         * */
        /*val intent = getMapIntent()
        if( intent.resolveActivity( packageManager ) != null ){
            buttonOpenGoogleMaps.visibility = View.VISIBLE
        }
        else{
            buttonOpenGoogleMaps.visibility = View.GONE
        }*/

        /*val intent = getMapIntent()
        if( intent.resolveActivity( packageManager ) == null ){
            openDownloadMapsAppDialog()
        }*/
    }


    /*
     * Método listener do ScrollView para que seja possível carregar os
     * conteúdos do layout principal sob demanda. Assim aliviando a
     * quantidade de memória e o tempo de carrgamento caso o layout
     * por completo fosse definido de uma única vez em activity_main.xml.
     *
     * Esse método listener não faz parte dos conteúdos de explicação
     * sobre as intenções do Google Maps.
     * */
    override fun onScrollChanged() {

        if( vs_content_section_2 != null ){
            vs_content_section_2.inflate()
        }
        else if( vs_content_section_3 != null ){
            vs_content_section_3.inflate()
        }
        else if( vs_content_section_4 != null ){
            vs_content_section_4.inflate()
        }
    }


    /*
     * Tópico ➙ Intenções explícitas (explicit intent)
     * */
    fun intencoesExplicitasImplicitIntent_Algoritmo_1( view: View ){
        /*
         * Somente uma ação (visualização, Intent.ACTION_VIEW) e o dado para a
         * ação (Uri presente em thiengoComBr) foram definidos na intenção
         * (webIntent).
         * */
        val thiengoComBr = Uri.parse( "https://www.thiengo.com.br" )
        val webIntent = Intent( Intent.ACTION_VIEW, thiengoComBr )
        startActivity( webIntent )
    }
    fun intencoesExplicitasImplicitIntent_Algoritmo_2( view: View ){
        /*
         * Apesar de não haver a definição explícita de uma ação e também o
         * armazenamento de algum dado, apesar disso a entidade de destino
         * foi definida (LoginActivity::class.java).
         * */
        val loginIntent = Intent( this, LoginActivity::class.java )
        startActivity( loginIntent )
    }
    fun intencoesExplicitasImplicitIntent_Algoritmo_3( view: View ){
        val shopping = Uri.parse( "google.navigation:q=-20.192710,-40.266240" )
        val routeIntent = Intent( Intent.ACTION_VIEW, shopping )

        /*
         * Aqui a definição explícita da entidade de destino está no método
         * setPackage(), mais precisamente a atividade correta (não
         * necessariamente é a atividade principal do app) do aplicativo de
         * pacote "com.google.android.apps.maps".
         * */
        routeIntent.setPackage( "com.google.android.apps.maps" )
        startActivity( routeIntent )
    }


    /*
     * Tópico ➙ Apresentação simples de um local específico
     * */
    fun apresentacaoSimplesDeUmLocalEspecifico_Algoritmo_1( view: View ){
        /*
         * A coordenada a seguir é a coordenada do Shopping Montserrat em Serra,
         * Espírito Santo - Brasil.
         * */
        val latitudeLongitude = "-20.192710,-40.266240"
        val zoom = 15
        val geo = "geo:$latitudeLongitude?z=$zoom"

        val geoUri = Uri.parse( geo )
        val intent = Intent( Intent.ACTION_VIEW, geoUri )

        /*
         * Aqui estamos definindo que: o aplicativo Google Maps seja
         * utilizado para a apresentação do local (latitudeLongitude) em
         * intent.
         *
         * A Intent a seguir é uma Intent explícita, ou seja, nós definimos que
         * uma determinada atividade (dentro do Google Maps App) deve ser
         * acionada.
         * */
        intent.setPackage( "com.google.android.apps.maps" )

        startActivity( intent )
    }


    /*
     * Tópico ➙ Evitando exceções quando a intenção não puder ser respondida
     * */
    fun evitandoExcecoesQuandoAIntencaoNaoPuderSerRespondida_Algoritmo_1( view: View ){

        val latitudeLongitude = "-20.192710,-40.266240"
        val zoom = 15
        val geo = "geo:$latitudeLongitude?z=$zoom"

        val geoUri = Uri.parse( geo )
        val intent = Intent( Intent.ACTION_VIEW, geoUri )

        intent.setPackage( "com.google.android.apps.maps" )

        if( intent.resolveActivity( packageManager ) != null ){
            startActivity( intent )
        }
        else{
            // TODO
        }
    }
    fun evitandoExcecoesQuandoAIntencaoNaoPuderSerRespondida_Algoritmo_2( view: View ){
        val latitudeLongitude = "-20.192710,-40.266240"
        val zoom = 15
        val geo = "geo:$latitudeLongitude?z=$zoom"

        var geoUri = Uri.parse( geo )
        var intent = Intent( Intent.ACTION_VIEW, geoUri )

        intent.setPackage( "com.google.android.apps.maps" )

        if( intent.resolveActivity( packageManager ) == null ){
        //if( intent.resolveActivity( packageManager ) != null ){ // Forçando a configuração para navegador mobile.
            /*
             * Utilizando a sintaxe genérica do Google Maps, Maps URLs.
             * Assim é certo que a versão Web do Google Maps será ao
             * menos solicitada no navegador do aparelho (se houver um).
             * */
            val actionMap = "api=1&map_action=map"
            val center = "center=$latitudeLongitude"
            val z = "zoom=$zoom"
            val web = "https://www.google.com/maps/@?$actionMap&$center&$z"

            geoUri = Uri.parse( web )
            intent = Intent( Intent.ACTION_VIEW, geoUri )
            //intent.setPackage( "com.android.chrome" ) // Para o Chrome mobile ser utilizado.
        }

        // A verificação de segurança ainda se faz necessária.
        if( intent.resolveActivity( packageManager ) != null ){
            startActivity( intent )
        }
        else{
            // TODO
        }
    }
    private fun getMapIntent() : Intent {

        val latitudeLongitude = "-20.192710,-40.266240"
        val zoom = 15
        val geo = "geo:$latitudeLongitude?z=$zoom"

        var geoUri = Uri.parse( geo )
        var intent = Intent( Intent.ACTION_VIEW, geoUri )

        intent.setPackage( "com.google.android.apps.maps" )

        if( intent.resolveActivity( packageManager ) == null ){
            /*
             * Utilizando a sintaxe genérica do Google Maps, Maps URLs.
             * Assim é certo que a versão Web do Google Maps será ao
             * menos solicitada no navegador do aparelho (se houver um).
             * */
            val actionMap = "api=1&map_action=map"
            val center = "center=$latitudeLongitude"
            val z = "zoom=$zoom"
            val web = "https://www.google.com/maps/@?$actionMap&$center&$z"

            geoUri = Uri.parse( web )
            intent = Intent( Intent.ACTION_VIEW, geoUri )
        }

        return intent
    }
    private fun openDownloadMapsAppDialog(){

        AlertDialog
            .Builder( this )
            .setMessage(
                "Instalar aplicativo do Google " +
                "Maps para visualizar a rota."
            )
            .setPositiveButton(
                "Instalar",
                {
                    dialog, id ->

                    /*
                     * Abre a página do aplicativo do Google Maps
                     * na Google Play Store.
                     * */
                    val pageUri = Uri.parse(
                        "https://play.google.com/store/apps/" +
                            "details?id=com.google.android.apps.maps"
                    )
                    val intent = Intent( Intent.ACTION_VIEW, pageUri )

                    intent.setPackage( "com.android.vending" )
                    startActivity( intent )
                }
            )
            .setNegativeButton(
                "Mais tarde",
                {
                    dialog, id ->

                    /*
                     * Apenas esconda o botão de acionamento do
                     * Google Maps e feche a caixa de diálogo.
                     * */
                    buttonOpenGoogleMaps.visibility = View.GONE
                    dialog.dismiss()
                }
            )
            .setCancelable( false )
            .create()
            .show()
    }


    /*
     * Tópico ➙ Você vai encontrar muito "por aí"
     * */
    fun voceVaiEncontrarMuitoPorAi_Algoritmo_1( view: View ){

        val latitudeLongitude = "-20.192710,-40.266240"
        val zoom = 15
        val geo = "geo:$latitudeLongitude?z=$zoom"

        var intent = Uri
            .parse( geo )
            .let{
                /*
                 * Abaixo, o parâmetro geoUri é exatamente o
                 * resultado de parse( geo ) - uma Uri. O
                 * retorno do lambda é a última linha de
                 * todo o código que vem depois de "geoUri ->".
                 *
                 * Poderíamos ter utilizado o parâmetro "it" ao
                 * invés de declarar "geoUri ->", mas perderíamos
                 * em leitura de código. "it" é o parâmetro padrão
                 * em blocos de código lambda quando se espera
                 * somente um parâmetro.
                 * */
                geoUri -> Intent( Intent.ACTION_VIEW, geoUri )
            }
            .apply{
                /*
                 * Diferente de let(), apply() não define um
                 * tipo função com um único parâmetro de entrada,
                 * o tipo função em apply() não contém nenhum
                 * parâmetro, dessa forma o objeto que invoca
                 * apply(), também conhecido como objeto
                 * receptor, é referenciado de maneira implícita
                 * dentro do lambda de apply(). Assim podemos
                 * invocar diretamente os membros desse objeto,
                 * como em "setPackage()", ou utilizar a palavra
                 * reservada "this" como em "this.setPackage()".
                 * */
                this.setPackage( "com.google.android.apps.maps" )
            }

        if( intent.resolveActivity( packageManager ) == null ){

            intent = Uri.parse(
                "https://play.google.com/store/apps/" +
                    "details?id=com.google.android.apps.maps"
                )
                .let{
                    pageAppUri -> Intent( Intent.ACTION_VIEW, pageAppUri )
                }
                .apply{
                    setPackage( "com.android.vending" )
                }
        }

        startActivity( intent )
    }


    /*
     * Tópico ➙ Busca por localizações: sintaxe e pontos mais próximos do usuário
     * */
    fun buscaPorLocalizacoesSintaxeEPontosMaisProximosDoUsuario_Algoritmo_1( view: View ){

        /*
         * Buscando por restaurantes próximos ao usuário - no
         * emulador de testes o usuário estava no Shopping
         * Montserrat em Serra, Espírito Santo - Brasil.
         * */
        val query = "restaurantes"
        val geo = "geo:0,0?q=$query"

        val geoSearchUri = Uri.parse( geo )
        val intent = Intent( Intent.ACTION_VIEW, geoSearchUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }


    /*
     * Tópico ➙ Trabalhando o raio da busca por localização e pontos mais próximos
     * */
    fun trabalhandoORaioDaBuscaPorLocalizacaoEPontosMaisProximos_Algoritmo_1( view: View ){

        val query = "restaurantes"
        val zoom = 20
        val geo = "geo:0,0?q=$query&z=$zoom"

        val geoSearchUri = Uri.parse( geo )
        val intent = Intent( Intent.ACTION_VIEW, geoSearchUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }


    /*
     * Tópico ➙ Pontos mais próximos de uma latitude e longitude definidos
     * */
    fun pontosMaisProximosDeUmaLatitudeELongitudeDefinidos_Algoritmo_1( view: View ){

        /*
         * Buscando por boxes de crossfit próximos ao Shopping Vitória
         * (-20.312800, -40.287858) em Vitória, ES - Brasil.
         * */
        val query = "crossfit"
        val latitudeLongitude = "-20.312800, -40.287858"
        val geo = "geo:$latitudeLongitude?q=$query"

        val geoSearchUri = Uri.parse( geo )
        val intent = Intent( Intent.ACTION_VIEW, geoSearchUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }


    /*
     * Tópico ➙ Busca por um local específico
     * */
    fun buscaPorUmLocalEspecifico_Algoritmo_1( view: View ){

        /*
         * Uri.encode() garante que o conteúdo utilizado como
         * parte da URI de mapa não usará caracteres não
         * aceitos em uma URI. Uri.encode() é muito importante
         * principalmente quando é o usuário do aplicativo que
         * fornece a entrada que será utilizada na busca em mapa.
         * */
        val vilaVelhaMall = """
            2418 Shopping Vila Velha, Vila Velha, Espírito Santo
        """.trim()
        val location = Uri.encode( vilaVelhaMall )
        val geo = "geo:0,0?q=$location"

        val geoSearchUri = Uri.parse( geo )
        val intent = Intent( Intent.ACTION_VIEW, geoSearchUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }


    /*
     * Tópico ➙ Buscando endereço de acordo com a posição atual do usuário
     * */
    fun buscandoEnderecoDeAcordoComAPosicaoAtualDoUsuario_Algoritmo_1( view: View ){

        val street = "Rua das Maritacas"
        val location = Uri.encode( street )
        val geo = "geo:0,0?q=$location"

        val geoSearchUri = Uri.parse( geo )
        val intent = Intent( Intent.ACTION_VIEW, geoSearchUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }


    /*
     * Tópico ➙ Buscando endereço de acordo com a latitude e longitude fornecidos
     * */
    fun buscandoEnderecoDeAcordoComALatitudeELongitudeFornecidos_Algoritmo_1( view: View ){

        /*
         * O algoritmo abaixo buscará pela mais próxima "Rua das
         * Maritacas” partindo da localização -20.200002, -40.227420
         * (Hospital Estadual Dr. Jayme Santos Neves).
         * */
        val street = "Rua das Maritacas"
        val location = Uri.encode( street )
        val latitudeLongitude = "-20.200002, -40.227420"
        val geo = "geo:$latitudeLongitude?q=$location"

        val geoSearchUri = Uri.parse( geo )
        val intent = Intent( Intent.ACTION_VIEW, geoSearchUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }


    /*
     * Tópico ➙ Definindo um rótulo para o endereço em busca
     * */
    fun definindoRotuloParaOEnderecoEmBusca_Algoritmo_1( view: View ){

        val label = Uri.encode(
            "Instituto de Ciências e Tecnologia da Cidade de Serra"
        )
        val latitudeLongitude = "-20.197531, -40.217126" // IFES - Campus Serra.
        val geo = "geo:0,0?q=$latitudeLongitude($label)"

        val geoSearchUri = Uri.parse( geo )
        val intent = Intent( Intent.ACTION_VIEW, geoSearchUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }


    /*
     * Tópico ➙ Apresentando rota e guia turn-by-turn (GPS)
     * */
    fun apresentandoRotaEGuiaTurnByTurnGps_Algoritmo_1( view: View ){

        val address = "Shopping Vitória, Vitória, Espírito Santo, Brasil"
        val location = Uri.encode( address )
        val navigation = "google.navigation:q=$location"

        val navigationUri = Uri.parse( navigation )
        val intent = Intent( Intent.ACTION_VIEW, navigationUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }


    /*
     * Tópico ➙ Apresentando rota com definição de meio de transporte
     * */
    fun apresentandoRotaComDefinicaoDeMeioDeTransporte_Algoritmo_1( view: View ){

        val address = "Shopping Vitória, Vitória, Espírito Santo, Brasil"
        val location = Uri.encode( address )
        val mode = "l" // Bicicleta.
        val navigation = "google.navigation:q=$location&mode=$mode"

        val navigationUri = Uri.parse( navigation )
        val intent = Intent( Intent.ACTION_VIEW, navigationUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }
    fun apresentandoRotaComDefinicaoDeMeioDeTransporte_Algoritmo_2( view: View ){

        val address = "Shopping Vitória, Vitória, Espírito Santo, Brasil"
        val location = Uri.encode( address )
        val mode = "bicycling" // Bicicleta.
        val navigation = "https://www.google.com/maps/dir/?api=1&" +
                "destination=$location&travelmode=$mode"

        val navigationUri = Uri.parse( navigation )
        val intent = Intent( Intent.ACTION_VIEW, navigationUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }
    fun apresentandoRotaComDefinicaoDeMeioDeTransporte_Algoritmo_3( view: View ){

        val origin = "Brasilândia, São Paulo, São Paulo, Brasil"
        val address = "Água Branca, São Paulo, São Paulo, Brasil"
        val location = Uri.encode( address )
        val mode = "transit" // Transporte público.
        val navigation = "https://www.google.com/maps/dir/?api=1&" +
                "destination=$location&travelmode=$mode&origin=$origin"

        val navigationUri = Uri.parse( navigation )
        val intent = Intent( Intent.ACTION_VIEW, navigationUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }
    fun apresentandoRotaComDefinicaoDeMeioDeTransporte_Algoritmo_4( view: View ){

        val address = "Shopping Vitória, Vitória, Espírito Santo, Brasil"
        val location = Uri.encode( address )
        val mode = "transit" // Transporte público.
        val navigation = "https://www.google.com/maps/dir/?api=1&" +
                "destination=$location&travelmode=$mode"

        val navigationUri = Uri.parse( navigation )
        val intent = Intent( Intent.ACTION_VIEW, navigationUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }


    /*
     * Tópico ➙ Apresentando rota com definição de itens a evitar
     * */
    fun apresentandoRotaComDefinicaoDeItensAEvitar_Algoritmo_1( view: View ){

        val address = """
            Ibitiquara, Cachoeiro de Itapemirim, Espírito Santo, Brasil
        """.trim()
        val location = Uri.encode( address )
        val avoid = "ht" // Evitar rodovias e pedágios.
        val navigation = "google.navigation:q=$location&avoid=$avoid"

        val navigationUri = Uri.parse( navigation )
        val intent = Intent( Intent.ACTION_VIEW, navigationUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }


    /*
     * Tópico ➙ Apresentando rotas com o uso de WayPoints (pontos obrigatórios em trajeto)
     * */
    fun apresentandoRotasComOUsoDeWaypointsPontosObrigatoriosEmTrajeto_Algoritmo_1( view: View ){

        val address = """
            Ibitiquara, Cachoeiro de Itapemirim, Espírito Santo, Brasil
        """.trim()
        val destination = "destination=${ Uri.encode( address ) }"

        /*
         * travelmode aceita os valore: driving (dirigindo); walking (andando);
         * bicycling (ciclismo); ou transit (transporte público ou de viagem - ônibus,
         * por exemplo).
         * */
        val mode = "travelmode=driving"

        /*
         * O caractere pipe, |, é utilizado para separar os pontos, locais,
         * nos quais o trajeto tem que passar até o destino (destination).
         *
         * Em plataformas onde WayPoints não são suportados, os pontos
         * serão ignorados.
         *
         * É possível fornecer de 1 a 9 pontos obrigatórios.
         *
         * Os WayPoints podem ser fornecidos como: endereços; nomes de locais; e
         * coordenadas, "latitude,longitude".
         * */
        val laranjeirasMall = """
            Shopping Laranjeiras, Serra, Espírito Santo, Brasil
        """.trim()

        val vitoriaMall = """
            Shopping Vitória, Vitória, Espírito Santo, Brasil
        """.trim()

        val vilaVelhaMall = """
            Shopping Vila Velha, Vila Velha, Espírito Santo, Brasil
        """.trim()

        val wayPoints = "waypoints=${
            Uri.encode( "$laranjeirasMall|$vitoriaMall|$vilaVelhaMall" )
        }".trim()

        val navigation = """
            https://www.google.com/maps/dir/?api=1&$destination&$mode&$wayPoints
        """.trim()
        val navigationUri = Uri.parse( navigation )
        val intent = Intent( Intent.ACTION_VIEW, navigationUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }
    fun apresentandoRotasComOUsoDeWaypointsPontosObrigatoriosEmTrajeto_Algoritmo_2( view: View ){

        val address = """
            Ibitiquara, Cachoeiro de Itapemirim, Espírito Santo, Brasil
        """.trim()
        val destination = "destination=${ Uri.encode( address ) }"

        val mode = "travelmode=driving"

        val laranjeirasMall = """
            Shopping Laranjeiras, Serra, Espírito Santo, Brasil
        """.trim()

        val vitoriaMall = """
            Shopping Vitória, Vitória, Espírito Santo, Brasil
        """.trim()

        val vilaVelhaMall = """
            Shopping Vila Velha, Vila Velha, Espírito Santo, Brasil
        """.trim()

        val wayPoints = "waypoints=${
            Uri.encode( "$laranjeirasMall|$vitoriaMall|$vilaVelhaMall" )
        }".trim()

        val hospital = Uri.encode( "Hospital Estadual Dr. Jayme Santos Neves" )
        val origin = "origin=$hospital"

        val navigation = "https://www.google.com/maps/dir/?" +
                "api=1&$destination&$mode&$wayPoints&$origin"
        val navigationUri = Uri.parse( navigation )
        val intent = Intent( Intent.ACTION_VIEW, navigationUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }


    /*
     * Tópico ➙ Trabalhando com o Street View
     * */
    fun trabalhandoComOStreetView_Algoritmo_1( view: View ){

        /*
         * Coordenadas da faculdade UCL em Serra, ES - Brasil.
         * */
        val uclCollege = "-20.202941,-40.219806"
        val streetView = "google.streetview:cbll=$uclCollege"

        val streetViewUri = Uri.parse( streetView )
        val intent = Intent( Intent.ACTION_VIEW, streetViewUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }
    fun trabalhandoComOStreetView_Algoritmo_2( view: View ){

        /*
         * PanoID de uma praça próxima à Catedral de Santa Sofia em
         * Kiev, Ucrânia.
         * */
        val kiev = "RJd2HuqmShMAAAQfCa3ulg"
        val streetView = "google.streetview:panoid=$kiev"

        val streetViewUri = Uri.parse( streetView )
        val intent = Intent( Intent.ACTION_VIEW, streetViewUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }
    fun trabalhandoComOStreetView_Algoritmo_3( view: View ){

        /*
         * PanoID do prédio de pós-graduação da UFES - Campus Goiabeiras.
         * */
        val posUfes = "VqQsDyJEGi0D768XkTXM9Q"
        val streetView = "google.streetview:panoid=$posUfes"


        val streetViewUri = Uri.parse( streetView )
        val intent = Intent( Intent.ACTION_VIEW, streetViewUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }
    fun trabalhandoComOStreetView_Algoritmo_4( view: View ){

        /*
         * Pirâmides em Giza. Os valores passados para o parâmetro cbp colocarão
         * o ângulo da câmera um pouco para cima e para o leste.
         * */
        val pyramidsInGiza = "29.9774614,31.1329645"
        val cbp = "0,30,0,0,-15"
        val streetView = "google.streetview:cbll=$pyramidsInGiza&cbp=$cbp"

        val streetViewUri = Uri.parse( streetView )
        val intent = Intent( Intent.ACTION_VIEW, streetViewUri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }


    /*
     * Tópico ➙ Utilizando plus code em intenções de mapa
     * */
    fun utilizandoPlusCodeEmIntencoesDeMapa_Algoritmo_1( view: View ){

        val plusCodeUclCollege = "http://plus.codes/58FXQQWH+VW"
        val uri = Uri.parse( plusCodeUclCollege )
        val intent = Intent( Intent.ACTION_VIEW, uri )

        intent.setPackage( "com.google.android.apps.maps" )
        startActivity( intent )
    }
}
