PGDMP      /                |           tpv    16.3    16.3                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            	           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            
           1262    16719    tpv    DATABASE     v   CREATE DATABASE tpv WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Spain.1252';
    DROP DATABASE tpv;
                postgres    false            �            1259    16720 	   categoria    TABLE     �   CREATE TABLE public.categoria (
    id_categoria character varying(25) NOT NULL,
    nombre character varying(35) NOT NULL,
    urlimagen character varying(150)
);
    DROP TABLE public.categoria;
       public         heap    postgres    false            �            1259    16723    detallefactura    TABLE       CREATE TABLE public.detallefactura (
    id_detallefactura integer NOT NULL,
    idfactura integer NOT NULL,
    producto character varying(30) NOT NULL,
    cantidad integer NOT NULL,
    precio double precision NOT NULL,
    descuento integer,
    total double precision
);
 "   DROP TABLE public.detallefactura;
       public         heap    postgres    false            �            1259    16726 $   detallefactura_id_detallefactura_seq    SEQUENCE     �   CREATE SEQUENCE public.detallefactura_id_detallefactura_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ;   DROP SEQUENCE public.detallefactura_id_detallefactura_seq;
       public          postgres    false    216                       0    0 $   detallefactura_id_detallefactura_seq    SEQUENCE OWNED BY     m   ALTER SEQUENCE public.detallefactura_id_detallefactura_seq OWNED BY public.detallefactura.id_detallefactura;
          public          postgres    false    217            �            1259    16727    factura    TABLE     �   CREATE TABLE public.factura (
    idfactura integer NOT NULL,
    nombre_usuario character varying(45) NOT NULL,
    hora_compra timestamp with time zone NOT NULL,
    tipo_pago character varying(25) NOT NULL
);
    DROP TABLE public.factura;
       public         heap    postgres    false            �            1259    16730    factura_idfactura_seq    SEQUENCE     �   CREATE SEQUENCE public.factura_idfactura_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.factura_idfactura_seq;
       public          postgres    false    218                       0    0    factura_idfactura_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.factura_idfactura_seq OWNED BY public.factura.idfactura;
          public          postgres    false    219            �            1259    16731 	   productos    TABLE     <  CREATE TABLE public.productos (
    codigo_barras character varying(30) NOT NULL,
    nombre character varying(30) NOT NULL,
    id_categoria character varying(25),
    url_codigobarras character varying(50) NOT NULL,
    iva integer NOT NULL,
    descuento integer NOT NULL,
    precio double precision NOT NULL
);
    DROP TABLE public.productos;
       public         heap    postgres    false            �            1259    16734    usuarios    TABLE     C  CREATE TABLE public.usuarios (
    dni character varying(10) NOT NULL,
    nombre character varying(20) NOT NULL,
    contrasenya character varying(30) NOT NULL,
    sesion_abierta smallint NOT NULL,
    fecha_creacion date NOT NULL,
    url_imagen character varying(100),
    privilegios character varying(20) NOT NULL
);
    DROP TABLE public.usuarios;
       public         heap    postgres    false            a           2604    16737     detallefactura id_detallefactura    DEFAULT     �   ALTER TABLE ONLY public.detallefactura ALTER COLUMN id_detallefactura SET DEFAULT nextval('public.detallefactura_id_detallefactura_seq'::regclass);
 O   ALTER TABLE public.detallefactura ALTER COLUMN id_detallefactura DROP DEFAULT;
       public          postgres    false    217    216            b           2604    16738    factura idfactura    DEFAULT     v   ALTER TABLE ONLY public.factura ALTER COLUMN idfactura SET DEFAULT nextval('public.factura_idfactura_seq'::regclass);
 @   ALTER TABLE public.factura ALTER COLUMN idfactura DROP DEFAULT;
       public          postgres    false    219    218            �          0    16720 	   categoria 
   TABLE DATA           D   COPY public.categoria (id_categoria, nombre, urlimagen) FROM stdin;
    public          postgres    false    215   _#       �          0    16723    detallefactura 
   TABLE DATA           t   COPY public.detallefactura (id_detallefactura, idfactura, producto, cantidad, precio, descuento, total) FROM stdin;
    public          postgres    false    216   #$                 0    16727    factura 
   TABLE DATA           T   COPY public.factura (idfactura, nombre_usuario, hora_compra, tipo_pago) FROM stdin;
    public          postgres    false    218   /)                 0    16731 	   productos 
   TABLE DATA           r   COPY public.productos (codigo_barras, nombre, id_categoria, url_codigobarras, iva, descuento, precio) FROM stdin;
    public          postgres    false    220   �.                 0    16734    usuarios 
   TABLE DATA           u   COPY public.usuarios (dni, nombre, contrasenya, sesion_abierta, fecha_creacion, url_imagen, privilegios) FROM stdin;
    public          postgres    false    221   10                  0    0 $   detallefactura_id_detallefactura_seq    SEQUENCE SET     T   SELECT pg_catalog.setval('public.detallefactura_id_detallefactura_seq', 164, true);
          public          postgres    false    217                       0    0    factura_idfactura_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.factura_idfactura_seq', 119, true);
          public          postgres    false    219            d           2606    16740    categoria categoria_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id_categoria);
 B   ALTER TABLE ONLY public.categoria DROP CONSTRAINT categoria_pkey;
       public            postgres    false    215            f           2606    16742 "   detallefactura detallefactura_pkey 
   CONSTRAINT     o   ALTER TABLE ONLY public.detallefactura
    ADD CONSTRAINT detallefactura_pkey PRIMARY KEY (id_detallefactura);
 L   ALTER TABLE ONLY public.detallefactura DROP CONSTRAINT detallefactura_pkey;
       public            postgres    false    216            h           2606    16744    factura factura_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.factura
    ADD CONSTRAINT factura_pkey PRIMARY KEY (idfactura);
 >   ALTER TABLE ONLY public.factura DROP CONSTRAINT factura_pkey;
       public            postgres    false    218            j           2606    16746    productos productos_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public.productos
    ADD CONSTRAINT productos_pkey PRIMARY KEY (codigo_barras);
 B   ALTER TABLE ONLY public.productos DROP CONSTRAINT productos_pkey;
       public            postgres    false    220            l           2606    16748    usuarios usuarios_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (dni);
 @   ALTER TABLE ONLY public.usuarios DROP CONSTRAINT usuarios_pkey;
       public            postgres    false    221            m           2606    16749     detallefactura detallefactura_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.detallefactura
    ADD CONSTRAINT detallefactura_fk FOREIGN KEY (idfactura) REFERENCES public.factura(idfactura) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;
 J   ALTER TABLE ONLY public.detallefactura DROP CONSTRAINT detallefactura_fk;
       public          postgres    false    4712    216    218            n           2606    16754    productos fk_categoria    FK CONSTRAINT     �   ALTER TABLE ONLY public.productos
    ADD CONSTRAINT fk_categoria FOREIGN KEY (id_categoria) REFERENCES public.categoria(id_categoria) ON UPDATE SET NULL ON DELETE SET NULL NOT VALID;
 @   ALTER TABLE ONLY public.productos DROP CONSTRAINT fk_categoria;
       public          postgres    false    4708    215    220            �   �   x�U�?�0���:Tm����?�q21����D?��$F�wy�w��?��^lh��w���a{�&z:k�Zj�8qq��ז�e/5&(ζd��C�3(���(zF=�{�k���cbb@�Z��`�o��鸎I,6�1�[SP��\SC�d�+ƪTqZ�ZVPB
Pr�}��LJ�`�L�      �   �  x��X�r�6<��_�"��я�z����ؤ|�m�.+����T*_�H�HQC_���4f����ٴ��U�Ȇ�+4o���5��&�H��.Ɨ��ƨr3GU~��X�a����@i�1xN#a���³�(���t�f$+����<�0��w]���+�����v��|� Q�:�-����z������FADA\Bu����v9/y���4Ũ��	�5QV��$ʹ�E���c*x[h8/[�=a]A60&Ό�afbL�i@��7��y]$���79&��Q�$��£$��L%�	���eq��Z��6i5a��LCF2�Z2^ڢudD�'#:ga�蜍dŬ���dvk�t��d��d��s��d�����`_{��gT�d�wpH�y���=���1I�㦖������ސ�����l�w�$c�''���l$'��aP?1�^X{�P1؟j�S��ܠ�Xo�e�~+w�%����.�f�צn_�r�n�J1��
"�	���}����|��yU&��pM:�� 2A�C�����2�r��H^:ʐ��G�7w�k��5�Q���,j=QO�QDC�Ե�%/��h'g{��N2�l'�p���D8�	��1)����f�i�� ���L���eB?)�"	]��|)P�v0!��`׾l�7my�k+�
��Y�4 \�r"=ޡq@ω�G�:k���OԀ��fu0��ꎌx�V�G�#묍�f��渭��n@���9���#�#G�[+�a �4�DUq��y�	��G�v�	��>�n�	�i�����D�|���(3 y�����<A��<�N<A݇{�n�����	�i����ؓ4 �1�mu��מ'h�S/��x����5���'\\�LAs�z�yyy�
��>~u5�]r�r}���.��^�W/��%Y�I�Ca*�������t����u�z�W��<�T��׶�,{e���~�)�\���>e�M:�&Q�76}�)t�\;C�?p����(���{i7kl���i`$�m�<ou7_���w�1�7�e�򽜽�8�D�M\�
�1SK�ݒ�?�UFF�船���0;�d��ft��_ ݵ7u�,_��M�\j��ϴ�z���xj���֡
l���z��o�uy[w��� f	�ncf\�]���*P3�#�(��*3�ׯ���q��4��S��$W����gw��.;����r>Ӧ�Ħ�S��3K���e�(�/����S�`��x/_e����*��}��KS1x���`3�l���(��G         j  x�u�Mo7���_�� �)ͱ@�=5���H��Ek���/���G�d<Ks�������������#�G�3��Q+R��> �~�z���ÿOw��ec-fF�v��������]]¶���A��mI��T��v��������߉G�
�a���hCߠll2炴��Q.�,�3�K�6Ln	�%��J%Ԗp]�m#.� �3n+��'#�����I�\�����hq$��QՎ�^w�v�6�+\p�L4#M��RT���7�:_�*��Dh�4�KQ�{���<�Uj4Y$S�ͩ/5U���0�����4p��j�	_j��f-6�KM��:6GM�KM�eD��	�ㅦ��H�yn^�xtLX���;/D8�@�}!���}� ם����{E�x�j�*0z���4�xjA�8�^h��T��8U}�i�I+a�
)��¥��d))֍b4�*�R�x��1R5�KII��������4��P��ܳ���7��M5٣dIw�����&O��;�բ���<����/�����Q�.�mVI�Op�P��Z��dY[O'&c�A[:i4�Jp��zT�y��x�M��$�fOi�מ;���!�W�B�5qhݻ���%M�9���b�z�	�S�+�nI����ޏ^���s��Z�4R2�b�1%�zv���!�T�$'�9��@d�r�bn�e5w�{��סLvdYL�@w ��+Nt�r�;�5�X��L��4j��/�b,&u,kI�8�#�F0�Y˝�͎b�6���у��8��Z:j���4����q��OU�����;;�`�9o/~Ts_I!��)s?����C&j��~T�eݍ��BM�K��Tg����KO\`6_�9h�hqJ�B�q&F���|s�JdsIZ�0Z.*�e
�3p�+T̃�,f]�9��e��G\]�9p�"���kV��q��X�j�l�����7)��<z6��ﲐ�����!5�8�X�sH;�z�}�^C^/��_����"�g<ܵ���\�LR</Κrm0��qd��2�r�Î׹6�x?�Fw��t G^�u˵%�x��2x)��
ػ��B�Ԯ�Q�f���o�O��\� �\�T���^
�֩���x���� �{_�k�)Y�|o���.B�/��E�>)}�f7��wl�2��;�4����Q�x�7H`�y�@���?.ׇ����mҗ�*�K�˝��q��!dÔ�GŽ�b���c��nj��2����>���=�=�z6��<EG\�u�
+�o H��Db^,p��1yMǅ��k
.3��K���u�����?��{�{�nk�۸��=}�ﻬKl�	���/��y��w��7�|CAl/�ο\��˷���/��oѡ�֊?������6�         x  x�]R�n�0<���_@mÚpDJ�8$��%�zq %V �6H���!�ްg�3;�`,����t5�	`�JS���Ex(�v'd��e1�uCw���� Nt)z�B�G�9��C�Ӥ�YA�5B"�\������G���H���{�q��3O0����V�C�l�|w�gG`�p�ʴ�v�����hd<�0r-��)ؗԙ�������$#���B�(�S�����{˹�����H�2���j��xA(ᮘЛ��+B��8����7P���#�)H
�'�xSMS��ѭ�Z�h����Ǉ#�7���*CG�eDg#)����v��]U��e��M��E�ě87�B��*Cx՝j�:�.���#��$_!��^��         �   x�342�,NI"NC Ӏ����D��T�؀337��8�H��3@/� �3��4�(3��1�����Ȓ3���� Y��	\[IiX[piAj�P-L��obfr~��SbQQbzb�!��pC�45��8��f�q��qqq O�0�     