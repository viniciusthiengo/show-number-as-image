package thiengo.com.br.shownumberasimage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /*
     * Método listener vinculado ao Button do layout para que qualquer
     * número possa ser utilizado em teste.
     * */
    public void generateNumberAsImage( View view ){
        EditText etNumber = findViewById(R.id.et_number);

        /*
         * Padrão Cláusula de Guarda: continua com o processamento do método
         * somente se uma condição mínima for aceita. Mais sobre este padrão
         * no link a seguir: https://www.thiengo.com.br/padrao-de-projeto-clausula-de-guarda
         * */
        if( etNumber.getText().toString().isEmpty() ){
            Toast.makeText(this, "Informe um número.", Toast.LENGTH_SHORT).show();
            return;
        }

        int number = Integer.parseInt( etNumber.getText().toString() );
        createNumberViews(
            this,
            (ViewGroup) findViewById(R.id.container), /* R.id.conatiner é o ViewGroup que conterá também o LinearLayout, aqui neste exemplo foi o LinearLayout, mas você pode utilizar qualquer outro ViewGroup em seu projeto. */
            number );
    }


    private void createNumberViews( Context context, ViewGroup container, int number ){
        removeNumberLayoutInContainer( container );

        ViewGroup containerNumberViews = createNumberViewsContainer( context );
        container.addView( containerNumberViews );

        /*
         * Loop para criar cada um dos ImageViews que representarão juntos
         * o número completo passado como parâmtero. Lembrando que o número
         * é um inteiro, logo, foi melhor transforma-lo em String, para depois
         * transforma-lo em char, depois String novamente e assim obter o
         * número certo. Isso para não ser necessário o trabalho com divisões
         * e mode, que seria bem mais exaustivo.
         * */
        char[] numberAsCharArray = String.valueOf( number ).toCharArray();

        for( char numberChar : numberAsCharArray ){

            /*
             * Estamos convertendo um char para String, pois caso utilizassemos
             * o char sem converte-lo, o inteiro retornado seria a posição do
             * número, em char, na tabela ASCII.
             * */
            int numberInt = Integer.parseInt( String.valueOf( numberChar ) );

            ImageView imageView = generateImageView( context, 48, 48 );
            imageView.setImageResource( getImageResource( numberInt ) );

            containerNumberViews.addView( imageView );
        }
    }


    /*
     * Removendo o LinearLayout já presente em tela com os números em
     * ImageViews, isso se já houver algum LinearLayout desse em tela.
     * */
    private void removeNumberLayoutInContainer(ViewGroup container){
        View linearLayout = findViewById(R.id.ll_container_image_views);
        if( linearLayout != null ){
            container.removeView( linearLayout );
        }
    }


    /*
     * Criando um novo LinearLayout para conter os novos ImageViews de
     * números. Já colocando a orientação como horizontal.
     * */
    private ViewGroup createNumberViewsContainer(Context context){
        /*
         * Note que ll_container_image_views está definido em um arquivo
         * XML específico para IDs no Android, mais precisamente o
         * /res/values/ids.xml:
         *
         * <?xml version="1.0" encoding="utf-8"?>
         * <resources>
         *      <item type="id" name="ll_container_image_views"/>
         * </resources>
         * */
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setId(R.id.ll_container_image_views);
        linearLayout.setOrientation( LinearLayout.HORIZONTAL );

        return linearLayout;
    }


    /*
     * Cria o ImageView que conterá o número atual. Definimos o tamanho e o
     * scaleType para termos uma boa visualização da imagem, mas não deixe
     * de criar uma imagem de cada número para cada um dos quatro tamanhos
     * exeigidos: mdpi, hdpi, xhdpi e xxhdpi. Mais sobre no link a seguir:
     * https://www.thiengo.com.br/suporte-de-tela-com-drawable-dpi-no-android
     *
     * Para lhe ajudar a definir o tamanho das imagens para os folders
     * drawable-mdpi, drawable-hdpi, drawable-xhdpi e drawable-xxhdpi
     * você pode utilizar o site: https://pixplicity.com/dp-px-converter.
     * Neste projeto de exemplo foi utilizado o tamanho 48 em mdpi para height
     * (altura) como referência, assim obtemos imagens de cada número com: 48
     * mdpi (equivalente a 48 pixels); 48 hdpi (equivalente a 72 pixels); 48
     * xhdpi (equivalente a 96 pixels); e 48 xxhdpi (equivalente a 144 pixels).
     * */
    private ImageView generateImageView( Context context, int width, int height ){
        int widthDp = pixelsInDp(context, width);
        int heightDp = pixelsInDp(context, height);
        ImageView imageView = new ImageView(context);

        imageView.setLayoutParams( new LinearLayout.LayoutParams(widthDp, heightDp) );
        imageView.setScaleType( ImageView.ScaleType.FIT_CENTER );
        return imageView;
    }


    /*
     * Retorna o valor em informado, em pixels, de acordo
     * com a desindade de tela.
     * */
    private int pixelsInDp(Context context, int pixels){

        float pixelsDp = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                pixels,
                context.getResources().getDisplayMetrics()
        );

        return (int) pixelsDp;
    }


    /*
     * Método com switch..case para cada um dos possíveis números.
     * */
    private int getImageResource( int number ){
        switch (number){
            case 1: return R.drawable.one;
            case 2: return R.drawable.two;
            case 3: return R.drawable.three;
            case 4: return R.drawable.four;
            case 5: return R.drawable.five;
            case 6: return R.drawable.six;
            case 7: return R.drawable.seven;
            case 8: return R.drawable.eight;
            case 9: return R.drawable.nine;
            default: return R.drawable.zero;
        }
    }
}
