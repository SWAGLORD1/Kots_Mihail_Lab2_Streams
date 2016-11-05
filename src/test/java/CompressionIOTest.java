import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;

public class CompressionIOTest {

    private static final String test1 = "Hello, world!";
    private static final String test2 = "The compression and the decompression leave an impression. Hahahahahahahaha!";
    private static final String test3 =
                    "Лев Николаевич Толстой\n" +
                    "ВОЙНА И МИР\n" +
                    "Том 1\n" +
                    "ЧАСТЬ ПЕРВАЯ\n" +
                    "\n" +
                    "I\n" +
                    "— Еh bien, mon prince. G\n" +
                    "Так говорила в июле 1805 года известная Анна Павловна Шерер, фрейлина и приближенная императрицы Марии Феодоровны, встречая важного и чиновного князя Василия, первого приехавшего на ее вечер. Анна Павловна кашляла несколько дней, у нее был грипп, как она говорила (грипп был тогда новое слово, употреблявшееся только редкими). В записочках, разосланных утром с красным лакеем, было написано без различия во всех:\n" +
                    "«Si vous n'avez rien de mieux\n" +
                    "[Если y вас, граф (или князь), нет в виду ничего лучшего и если перспектива вечера у бедной больной не слишком вас пугает, то я буду очень рада видеть вас нынче у себя между семью и десятью часами. Анна Шерер.]\n" +
                    "— Dieu, quelle virulente sortie [О! какое жестокое нападение!] — отвечал, нисколько не смутясь такою встречей, вошедший князь, в придворном, шитом мундире, в чулках, башмаках, при звездах, с светлым выражением плоского лица. Он говорил на том изысканном французском языке, на котором не только говорили, но и думали наши деды, и с теми тихими, покровительственными интонациями, которые свойственны состаревшемуся в свете и при дворе значительному человеку. Он подошел к Анне Павловне, поцеловал ее руку, подставив ей свою надушенную и сияющую лысину, и покойно уселся на диване.\n" +
                    "— Avant tout dites moi, comment vous allez, ch\n" +
                    "— Как можно быть здоровой… когда нравственно страдаешь? Разве можно оставаться спокойною в наше время, когда есть у человека чувство? — сказала Анна Павловна. — Вы весь вечер у меня, надеюсь?\n" +
                    "— А праздник английского посланника? Нынче середа. Мне надо показаться там, — сказал князь. — Дочь заедет за мной и повезет меня.\n" +
                    "— Я думала, что нынешний праздник отменен. Je vous avoue que toutes ces f\n" +
                    "— Ежели бы знали, что вы этого хотите, праздник бы отменили, — сказал князь, по привычке, как заведенные часы, говоря вещи, которым он и не хотел, чтобы верили.\n" +
                    "— Ne me tourmentez pas. Eh bien, qu'a-t-on d\n" +
                    "— Как вам сказать? — сказал князь холодным, скучающим тоном. — Qu'a-t-on d\n" +
                    "Быть энтузиасткой сделалось ее общественным положением, и иногда, когда ей даже того не хотелось, она, чтобы не обмануть ожиданий людей, знавших ее, делалась энтузиасткой. Сдержанная улыбка, игравшая постоянно на лице Анны Павловны, хотя и не шла к ее отжившим чертам, выражала, как у избалованных детей, постоянное сознание своего милого недостатка, от которого она не хочет, не может и не находит нужным исправляться.\n" +
                    "В середине разговора про политические действия Анна Павловна разгорячилась.\n" +
                    "— Ах, не говорите мне про Австрию! Я ничего не понимаю, может быть, но Австрия никогда не хотела и не хочет войны. Она предает нас. Россия одна должна быть спасительницей Европы. Наш благодетель знает свое высокое призвание и будет верен ему. Вот одно, во что я верю. Нашему доброму и чудному государю предстоит величайшая роль в мире, и он так добродетелен и хорош, что Бог не оставит его, и он исполнит свое призвание задавить гидру революции, которая теперь еще ужаснее в лице этого убийцы и злодея. Мы одни должны искупить кровь праведника… На кого нам надеяться, я вас спрашиваю?… Англия с своим коммерческим духом не поймет и не может понять всю высоту души императора Александра. Она отказалась очистить Мальту. Она хочет видеть, ищет заднюю мысль наших действий. Что они сказали Новосильцову?… Ничего. Они не поняли, они не могут понять самоотвержения нашего императора, который ничего не хочет для себя и всё хочет для блага мира. И что они обещали? Ничего. И что обещали, и того не будет! Пруссия уж объявила, что Бонапарте непобедим и что вся Европа ничего не может против него… И я не верю ни в одном слове ни Гарденбергу, ни Гаугвицу. Cette fameuse neutralit\n" +
                    "— Я думаю, — сказал князь улыбаясь, — что ежели бы вас послали вместо нашего милого Винценгероде, вы бы взяли приступом согласие прусского короля. Вы так красноречивы. Вы дадите мне чаю?\n" +
                    "— Сейчас. A propos, — прибавила она, опять успокоиваясь, — нынче у меня два очень интересные человека, le vicomte de MorteMariet, il est alli\n" +
                    "— А! Я очень рад буду, — сказал князь. — Скажите, — прибавил он, как будто только что вспомнив что-то и особенно-небрежно, тогда как то, о чем он спрашивал, было главною целью его посещения, — правда, что l'imp\n" +
                    "Анна Павловна почти закрыла глаза в знак того, что ни она, ни кто другой не могут судить про то, что угодно или нравится императрице.\n" +
                    "— Monsieur le baron de Funke a\n" +
                    "Князь равнодушно замолк. Анна Павловна, с свойственною ей придворною и женскою ловкостью и быстротою такта, захотела и щелконуть князя за то, что он дерзнул так отозваться о лице, рекомендованном императрице, и в то же время утешить его.\n" +
                    "— Mais\n" +
                    "Князь наклонился в знак уважения и признательности.\n" +
                    "— Я часто думаю, — продолжала Анна Павловна после минутного молчания, подвигаясь к князю и ласково улыбаясь ему, как будто выказывая этим, что политические и светские разговоры кончены и теперь начинается задушевный, — я часто думаю, как иногда несправедливо распределяется счастие жизни. За что вам судьба дала таких двух славных детей (исключая Анатоля, вашего меньшого, я его не люблю, — вставила она безапелляционно, приподняв брови) — таких прелестных детей? А вы, право, менее всех цените их и потому их не стоите.\n" +
                    "И она улыбнулась своею восторженною улыбкой.\n" +
                    "— Que voulez-vous? Lafater aurait dit que je n'ai pas la bosse de la paterienit\n" +
                    "— Перестаньте шутить. Я хотела серьезно поговорить с вами. Знаете, я недовольна вашим меньшим сыном. Между нами будь сказано (лицо ее приняло грустное выражение), о нем говорили у ее величества и жалеют вас…\n" +
                    "Князь не отвечал, но она молча, значительно глядя на него, ждала ответа. Князь Василий поморщился.\n" +
                    "— Что вы хотите, чтоб я делал! — сказал он наконец. — Вы знаете, я сделал для их воспитания все, что может отец, и оба вышли des imb\n" +
                    "— И зачем родятся дети у таких людей, как вы? Ежели бы вы не были отец, я бы ни в чем не могла упрекнуть вас, — сказала Анна Павловна, задумчиво поднимая глаза.\n" +
                    "— Je suis votre [Я ваш] верный раб, et\n" +
                    "Анна Павловна задумалась.\n" +
                    "— Вы никогда не думали о том, чтобы женить вашего блудного сына Анатоля? Говорят, — сказала она, — что старые девицы ont la manie des Marieiages. [имеют манию женить.] Я еще не чувствую за собою этой слабости, но у меня есть одна petite personne [маленькая особа], которая очень несчастлива с отцом, une parente\n" +
                    "— Нет, вы знаете ли, что этот Анатоль мне стоит 40.000 в год, — сказал он, видимо, не в силах удерживать печальный ход своих мыслей. Он помолчал.\n" +
                    "— Что будет через пять лет, если это пойдет так? Voil\n" +
                    "— Отец очень богат и скуп. Он живет в деревне. Знаете, этот известный князь Болконский, отставленный еще при покойном императоре и прозванный прусским королем. Он очень умный человек, но со странностями и тяжелый. La pauvre petite est malheureuse, comme les pierres. [Бедняжка несчастлива, как камни.] У нее брат, вот что недавно женился на Lise Мейнен, адъютант Кутузова. Он будет нынче у меня.\n" +
                    "— Ecoutez, chpan, comme mon староста m'crit des [как пишет мне мой староста] донесенья: покой-ер-п!. Она хорошей фамилии и богата. Всё, что мне нужно.\n" +
                    "И он с теми свободными и фамильярными, грациозными движениями, которые его отличали, взял за руку фрейлину, поцеловал ее и, поцеловав, помахал фрейлинскою рукой, развалившись на креслах и глядя в сторону.\n" +
                    "— Attendez [Подождите], — сказала Анна Павловна, соображая. — Я нынче же поговорю Lise (la femme du jeune Болконский). [с Лизой (женой молодого Болконского).] И, может быть, это уладится. Ce sera dans votre famille, que je ferai mon apprentissage de vieille fille. [Я в вашем семействе начну обучаться ремеслу старой девки.]\n";

    private void checkInformationIntegrity(String str) throws IOException {
        ByteArrayOutputStream code = new ByteArrayOutputStream(str.getBytes(Charset.defaultCharset()).length);
        CompressionOutputStream os = new CompressionOutputStream(code);
        os.write(str.getBytes(Charset.defaultCharset()));
        os.flush();
        CompressionInputStream is = new CompressionInputStream(new ByteArrayInputStream(code.toByteArray()));
        byte[] result = new byte[str.getBytes().length];
        is.read(result);
        Assert.assertTrue(new String(result,Charset.defaultCharset()).equals(str));
        code.close();
        is.close();
        os.close();

        code = new ByteArrayOutputStream(str.getBytes(Charset.defaultCharset()).length);
        os = new CompressionOutputStream(code);
        os.write(str.getBytes(Charset.defaultCharset()),0,str.getBytes(Charset.defaultCharset()).length);
        os.flush();
        is = new CompressionInputStream(new ByteArrayInputStream(code.toByteArray()));
        is.read(result,0,str.getBytes(Charset.defaultCharset()).length);
        Assert.assertTrue(new String(result,Charset.defaultCharset()).equals(str));
        code.close();
        is.close();
        os.close();

        code = new ByteArrayOutputStream(str.getBytes(Charset.defaultCharset()).length);
        os = new CompressionOutputStream(code);
        for(byte b : str.getBytes(Charset.defaultCharset()))
            os.write(b);
        os.flush();
        is = new CompressionInputStream(new ByteArrayInputStream(code.toByteArray()));
        for(int i=0;i<result.length;i++)
            result[i] = (byte)is.read();
        Assert.assertTrue(new String(result,Charset.defaultCharset()).equals(str));
        code.close();
        is.close();
        os.close();
    }

    private void checkSizeDecrease(String str) throws IOException {
        OutputStream code = new ByteArrayOutputStream(str.getBytes(Charset.defaultCharset()).length);
        CompressionOutputStream os = new CompressionOutputStream(code);
        os.write(str.getBytes(Charset.defaultCharset()));
        os.flush();
        Assert.assertTrue(str.getBytes(Charset.defaultCharset()).length >= code.toString().getBytes(Charset.defaultCharset()).length);
        code.close();
        os.close();
    }

    @Test
    public void check_empty() {
        try {
            checkInformationIntegrity("");
            checkSizeDecrease("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void check_simple() {
        try {
            checkInformationIntegrity(test1);
            checkSizeDecrease(test1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void check_advanced() {
        try {
            checkInformationIntegrity(test2);
            checkSizeDecrease(test2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void check_big() {
        try {
            checkInformationIntegrity(test3);
            checkSizeDecrease(test3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
