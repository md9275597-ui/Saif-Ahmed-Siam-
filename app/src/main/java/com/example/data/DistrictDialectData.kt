package com.example.data

import com.example.model.DistrictDialect
import com.example.model.ModelStatus

object DistrictDialectData {

    val allDistricts: List<DistrictDialect> = listOf(
        // DHAKA DIVISION (13)
        DistrictDialect("dhaka", "ঢাকা", "Dhaka", "ঢাকা বিভাগ", "Dhaka Division", "ঢাকাইয়া / পুরান ঢাকা", "আরে মিয়া, কি খবর তোমার? বিরিয়ানি খাইয়া আইছো না কি?", "আরে ভাই, আপনার কী খবর? বিরিয়ানি খেয়ে এসেছেন নাকি?", "টান ও দ্রুত উচ্চারণ, 'আইছো', 'গেছো'", ModelStatus.READY, "dhaka_m", "dhaka_f", "মিয়া, বাওয়া, লও"),
        DistrictDialect("gazipur", "গাজীপুর", "Gazipur", "ঢাকা বিভাগ", "Dhaka Division", "ভাওয়াল অঞ্চল", "কনে যাচ্ছো গো? আইজগা বাজারে কাম আছে নাকি?", "কোথায় যাচ্ছেন? আজকে বাজারে কাজ আছে নাকি?", "'কনে', 'আইজগা'", ModelStatus.READY, "gazi_m", "gazi_f"),
        DistrictDialect("narayanganj", "নারায়ণগঞ্জ", "Narayanganj", "ঢাকা বিভাগ", "Dhaka Division", "শীতলক্ষ্যা পাড়", "নদীত যাবা নাকি? নৌকা তো রেডি আছে!", "নদীতে যাবেন নাকি? নৌকা প্রস্তুত আছে!", "নরম ও টানযুক্ত স্বরভঙ্গি", ModelStatus.READY, "nganj_m", "nganj_f"),
        DistrictDialect("narsingdi", "নরসিংদী", "Narsingdi", "ঢাকা বিভাগ", "Dhaka Division", "মেঘনা অববাহিকা", "হেতারা কি কইতাছে হুনছো? চল যাইয়া দেহি।", "ওরা কী বলছে শুনেছ? চলো গিয়ে দেখি।", "'শ' এর স্থানে 'হ', 'দেহি'", ModelStatus.READY, "narsingdi_m", "narsingdi_f"),
        DistrictDialect("tangail", "টাঙ্গাইল", "Tangail", "ঢাকা বিভাগ", "Dhaka Division", "যমুনা তীরবর্তী", "চমচম খাইবা নাকি? আমাগো দেশি মিষ্টি খুব সোয়াদ!", "চমচম খাবেন নাকি? আমাদের মিষ্টি অনেক সুস্বাদু!", "'আমাগো', 'সোয়াদ'", ModelStatus.READY, "tangail_m", "tangail_f"),
        DistrictDialect("kishoreganj", "কিশোরগঞ্জ", "Kishoreganj", "ঢাকা বিভাগ", "Dhaka Division", "হাওর পার্শ্ববর্তী", "অ ভাই, অইন্য দিকে চান কেরে? এইহানে আহেন।", "ও ভাই, অন্য দিকে তাকাচ্ছেন কেন? এখানে আসুন।", "'কেরে', 'এইহানে'", ModelStatus.READY, "kishore_m", "kishore_f"),
        DistrictDialect("manikganj", "মানিকগঞ্জ", "Manikganj", "ঢাকা বিভাগ", "Dhaka Division", "কালিগঙ্গা আঞ্চলিক", "আজকে গুড়ের পায়েস হইবো, ঘরে যাইয়ো কিন্তু।", "আজকে গুড়ের পায়েস হবে, ঘরে যাবেন কিন্তু।", "'হইবো', 'যাইয়ো'", ModelStatus.COMING_SOON, "manik_m", "manik_f"),
        DistrictDialect("munshiganj", "মুন্সিগঞ্জ", "Munshiganj", "ঢাকা বিভাগ", "Dhaka Division", "বিক্রমপুরী কথ্য", "বিক্রমপুরের পোলাগো বুদ্ধি দ্যাখো, কাজটা কেমন কইরা দিলো!", "বিক্রমপুরের ছেলেদের বুদ্ধি দেখো, কাজটি কেমন সুন্দরভাবে করল!", "স্পষ্ট ও জোরালো শব্দচয়ন, 'পোলাগো'", ModelStatus.READY, "munshi_m", "munshi_f"),
        DistrictDialect("madaripur", "মাদারীপুর", "Madaripur", "ঢাকা বিভাগ", "Dhaka Division", "আড়িয়াল খাঁ তীরবর্তী", "হেই কালে কি কতা কইছিলা মনে আছে?", "সেই সময়ে কী কথা বলেছিলে মনে আছে?", "ফরিদপুরি-বরিশালী ট্রানজিশন", ModelStatus.COMING_SOON, "madari_m", "madari_f"),
        DistrictDialect("gopalganj", "গোপালগঞ্জ", "Gopalganj", "ঢাকা বিভাগ", "Dhaka Division", "মধুমতী তীরবর্তী", "অহন কনে যাইবেন কন, ঘাটে নাও বাঁধা আছে।", "এখন কোথায় যাবেন বলুন, ঘাটে নৌকা বাঁধা আছে।", "'অহন', 'নাও'", ModelStatus.READY, "gopal_m", "gopal_f"),
        DistrictDialect("faridpur", "ফরিদপুর", "Faridpur", "ঢাকা বিভাগ", "Dhaka Division", "ফরিদপুরি কথ্য", "ওরে বাবারে, এত বড় মাছ কইত্তে পাইলেন?", "ওরে বাবা, এত বড় মাছ কোথা থেকে পেলেন?", "আবেগীয় টান, 'কইত্তে'", ModelStatus.READY, "farid_m", "farid_f"),
        DistrictDialect("rajbari", "রাজবাড়ী", "Rajbari", "ঢাকা বিভাগ", "Dhaka Division", "পদ্মা তীরের উপভাষা", "ইলিশ ভাজা দিয়া গরম ভাত খাইতে যে মজা!", "ইলিশ ভাজা দিয়ে গরম ভাত খেতে কী মজা!", "পদ্মা পারের সুর", ModelStatus.COMING_SOON, "rajbari_m", "rajbari_f"),
        DistrictDialect("shariatpur", "শরীয়তপুর", "Shariatpur", "ঢাকা বিভাগ", "Dhaka Division", "মেঘনা আঞ্চলিক", "আইজকার দিনে নদীর পাড়ে বাতাসটা খুব ভালা লাগতাছে।", "আজকের দিনে নদীর পাড়ে বাতাসটা খুব ভালো লাগছে।", "প্রবহমান উচ্চারণ", ModelStatus.COMING_SOON, "shariat_m", "shariat_f"),

        // CHITTAGONG DIVISION (11)
        DistrictDialect("chittagong", "চট্টগ্রাম", "Chittagong", "চট্টগ্রাম বিভাগ", "Chittagong Division", "চাটগাঁইয়া ভাষা", "অনা কেন আছন? আঁই গম আছি, আঁরার দেশত আইয়্যুন!", "আপনি কেমন আছেন? আমি ভালো আছি, আমাদের দেশে আসুন!", "টোনাল স্বরভঙ্গি, 'গম', 'আঁই', 'অনা'", ModelStatus.READY, "ctg_m", "ctg_f", "গম (ভালো), আঁই (আমি)"),
        DistrictDialect("coxsbazar", "কক্সবাজার", "Cox's Bazar", "চট্টগ্রাম বিভাগ", "Chittagong Division", "উপকূলীয় চাটগাঁইয়া", "দরিয়ার পাড়ে যাইয়ুম, সুন্দর বাতাস আর ঢেউ দ্যাখোম।", "সমুদ্রের পাড়ে যাব, সুন্দর বাতাস আর ঢেউ দেখব।", "উপকূলীয় টোন", ModelStatus.READY, "cox_m", "cox_f"),
        DistrictDialect("comilla", "কুমিল্লা", "Comilla", "চট্টগ্রাম বিভাগ", "Chittagong Division", "কুমিল্লা কথ্য", "ক্যাবা আছেন ভাই? রসমলাই খাইবেন নি?", "কেমন আছেন ভাই? রসমলাই খাবেন কি?", "'ক্যাবা', 'নি' প্রত্যয়", ModelStatus.READY, "comilla_m", "comilla_f"),
        DistrictDialect("brahmanbaria", "ব্রাহ্মণবাড়িয়া", "Brahmanbaria", "চট্টগ্রাম বিভাগ", "Chittagong Division", "তিতাস পারের সুর", "আমাগো দেশে আইসো, তিতাস নদীর রূপ দেইখা যাও।", "আমাদের এলাকায় আসো, তিতাস নদীর রূপ দেখে যাও।", "জোরালো প্রত্যয়, 'আমাগো'", ModelStatus.READY, "bbaria_m", "bbaria_f"),
        DistrictDialect("chandpur", "চাঁদপুর", "Chandpur", "চট্টগ্রাম বিভাগ", "Chittagong Division", "মোহনা আঞ্চলিক", "পদ্মা-মেঘনার তাজা ইলিশ আইজকা পাক করুম।", "পদ্মা-মেঘনার তাজা ইলিশ আজকে রান্না করব।", "'পাক করুম'", ModelStatus.READY, "chand_m", "chand_f"),
        DistrictDialect("feni", "ফেনী", "Feni", "চট্টগ্রাম বিভাগ", "Chittagong Division", "ফেনী আঞ্চলিক", "হগলতে মিলি আইজকা মেলায় ঘুরতে যাইয়াম।", "সবাই মিলে আজকে মেলায় ঘুরতে যাব।", "নোয়াখালী-চাটগাঁইয়া মিলন", ModelStatus.READY, "feni_m", "feni_f"),
        DistrictDialect("lakshmipur", "লক্ষ্মীপুর", "Lakshmipur", "চট্টগ্রাম বিভাগ", "Chittagong Division", "উপকূলীয় ধারা", "আন্নে কিয়া কইলেন? আঁই তো হিডা বুঝিনো!", "আপনি কী বললেন? আমি তো সেটা বুঝিনি!", "'আন্নে', 'হিডা'", ModelStatus.READY, "laksh_m", "laksh_f"),
        DistrictDialect("noakhali", "নোয়াখালী", "Noakhali", "চট্টগ্রাম বিভাগ", "Chittagong Division", "নোয়াখাইল্লা ভাষা", "আন্নেরা ক্যানে আছেন? হগলে আঁর লগে আইয়েন, খানা খাইবেন।", "আপনারা কেমন আছেন? সবাই আমার সঙ্গে আসুন, খাবার খাবেন।", "'প'/'ফ' -> 'হ', পানি->হানি, 'আন্নে'", ModelStatus.READY, "noakhali_m", "noakhali_f", "হানি (পানি), হাইজ্যাম (খাব)"),
        DistrictDialect("khagrachhari", "খাগড়াছড়ি", "Khagrachhari", "চট্টগ্রাম বিভাগ", "Chittagong Division", "পাহাড়ি রূপ", "পাহাড়ের বাঁকে বাঁকে মেঘ নামি আইসে, কী সুন্দর দৃশ্য!", "পাহাড়ের বাঁকে বাঁকে মেঘ নেমে এসেছে, কী সুন্দর দৃশ্য!", "পাহাড়ি সুরেল ধ্বনি", ModelStatus.COMING_SOON, "khagra_m", "khagra_f"),
        DistrictDialect("rangamati", "রাঙামাটি", "Rangamati", "চট্টগ্রাম বিভাগ", "Chittagong Division", "কাপ্তাই আঞ্চলিক", "হ্রদের পানিতে নাও ভাসাইয়া দূর পাহাড়ে যাই।", "হ্রদের জলে নৌকা ভাসিয়ে দূরের পাহাড়ে যাই।", "শান্ত পাহাড়ি সুর", ModelStatus.COMING_SOON, "rang_m", "rang_f"),
        DistrictDialect("bandarban", "বান্দরবান", "Bandarban", "চট্টগ্রাম বিভাগ", "Chittagong Division", "সাঙ্গু অববাহিকা", "মেঘের দেশে তোমারে স্বাগতম, ঝর্ণার শব্দ শোনো!", "মেঘের দেশে তোমাকে স্বাগতম, ঝর্ণার শব্দ শোনো!", "উচ্চ পিচ বিশিষ্ট সুর", ModelStatus.COMING_SOON, "bandar_m", "bandar_f"),

        // RAJSHAHI DIVISION (8)
        DistrictDialect("rajshahi", "রাজশাহী", "Rajshahi", "রাজশাহী বিভাগ", "Rajshahi Division", "বরেন্দ্রী উপভাষা", "কেমন আছো তোমরা? ফজলি আর ল্যাংড়া আমের মওসুম আইলো!", "কেমন আছো তোমরা? ফজলি আর ল্যাংড়া আমের মৌসুম এলো!", "মৃদু টান, 'আইলো', 'গেলা'", ModelStatus.READY, "raj_m", "raj_f"),
        DistrictDialect("natore", "নাটোর", "Natore", "রাজশাহী বিভাগ", "Rajshahi Division", "চলনবিলের সুর", "কাঁচাগোল্লা খাইয়া দ্যাখছেন নি? এর মতন সোয়াদ আর নাই।", "কাঁচাগোল্লা খেয়ে দেখেছেন কি? এর মতো স্বাদ আর নেই।", "মিষ্টি রসালো টান", ModelStatus.READY, "natore_m", "natore_f"),
        DistrictDialect("naogaon", "নওগাঁ", "Naogaon", "রাজশাহী বিভাগ", "Rajshahi Division", "উত্তর বরেন্দ্র", "পাহাড়পুর বৌদ্ধবিহার দেইখা আসলাম, মনটা জুড়ায়ে গেলো।", "পাহাড়পুর বৌদ্ধবিহার দেখে আসলাম, মনটা জুড়িয়ে গেল।", "ধীর ও শান্ত উচ্চারণ", ModelStatus.COMING_SOON, "nao_m", "nao_f"),
        DistrictDialect("chapainawabganj", "চাঁপাইনবাবগঞ্জ", "Chapainawabganj", "রাজশাহী বিভাগ", "Rajshahi Division", "গম্ভীরা অঞ্চলের সুর", "ও নানা, তুমি কনে যাও গো? গম্ভীরা গানের আসর বসবে যে!", "ও নানা, তুমি কোথায় যাচ্ছ? গম্ভীরা গানের আসর বসবে যে!", "গম্ভীরা রূপের নাটুকে রসিক সুর", ModelStatus.READY, "chapai_m", "chapai_f"),
        DistrictDialect("pabna", "পাবনা", "Pabna", "রাজশাহী বিভাগ", "Rajshahi Division", "পাবনা রূপ", "ইচ্ছামতী নদীর পাড়ে বইস্যা বাতাস খাইতে খুব আরাম।", "ইচ্ছামতী নদীর পাড়ে বসে বাতাস খেতে খুব আরাম।", "'বইস্যা', 'খাইস্যা'", ModelStatus.READY, "pabna_m", "pabna_f"),
        DistrictDialect("sirajganj", "সিরাজগঞ্জ", "Sirajganj", "রাজশাহী বিভাগ", "Rajshahi Division", "যমুনা পাড়ের সুর", "তাঁতের শাড়ি দেইখা যাইও, আমাগো কারিগরদের হাতের জাদু!", "তাঁতের শাড়ি দেখে যেও, আমাদের কারিগরদের হাতের জাদু!", "দ্রুত ও স্পষ্ট উচ্চারণ", ModelStatus.COMING_SOON, "siraj_m", "siraj_f"),
        DistrictDialect("bogra", "বগুড়া", "Bogra", "রাজশাহী বিভাগ", "Rajshahi Division", "বগুড়ার আঞ্চলিক কথ্য", "কিরে বাপ, বগুড়ার দই খাইবিনা নাকি? এমন খাঁটি দই আর কোন্ঠে পাইবু!", "কিরে ভাই, বগুড়ার দই খাবি নাকি? এমন খাঁটি দই আর কোথায় পাবি!", "'কোন্ঠে', 'পাইবু', বিশেষ টান", ModelStatus.READY, "bogra_m", "bogra_f"),
        DistrictDialect("joypurhat", "জয়পুরহাট", "Joypurhat", "রাজশাহী বিভাগ", "Rajshahi Division", "উত্তরাঞ্চলীয় বরেন্দ্রী", "মাঠে সোনা ফলছে গো, আইজকা চলো ফসল কাটতে যাই।", "মাঠে সোনা ফলেছে, আজকে চলো ফসল কাটতে যাই।", "সরল উচ্চারণ", ModelStatus.COMING_SOON, "joypur_m", "joypur_f"),

        // KHULNA DIVISION (10)
        DistrictDialect("khulna", "খুলনা", "Khulna", "খুলনা বিভাগ", "Khulna Division", "দক্ষিণ-পশ্চিমাঞ্চলীয়", "সুন্দরবনের মধু আর গলদা চিংড়ি খাইয়ে আসপা নাকি?", "সুন্দরবনের মধু আর গলদা চিংড়ি খেয়ে আসবে নাকি?", "'আসপা', 'খাইয়ে'", ModelStatus.READY, "khulna_m", "khulna_f"),
        DistrictDialect("bagerhat", "বাগেরহাট", "Bagerhat", "খুলনা বিভাগ", "Khulna Division", "ষাটগম্বুজ সুর", "ঐতিহাসিক গম্বুজ মসজিদে যাইয়ে দু'হাত তুলে দোয়া করি।", "ঐতিহাসিক গম্বুজ মসজিদে গিয়ে দু'হাত তুলে দোয়া করি।", "'যাইয়ে'", ModelStatus.COMING_SOON, "bager_m", "bager_f"),
        DistrictDialect("satkhira", "সাতক্ষীরা", "Satkhira", "খুলনা বিভাগ", "Khulna Division", "সুন্দরবনীয় কথ্য", "ঘেরের মাছ দেখে আসপা? অনেক বড় বড় মাছ হইছে রে!", "ঘেরের মাছ দেখে আসবে? অনেক বড় বড় মাছ হয়েছে!", "'আসপা', 'হইছে রে'", ModelStatus.READY, "sat_m", "sat_f"),
        DistrictDialect("jessore", "যশোর", "Jessore", "খুলনা বিভাগ", "Khulna Division", "যশোরে আঞ্চলিক কথ্য", "খেজুরের গুড়ের পাটালি খায়ে আসপা নাকি? যশোরের গুড় বিশ্বখ্যাত!", "খেজুর গুড়ের পাটালি খেয়ে আসবে নাকি? যশোরের গুড় বিশ্বখ্যাত!", "নরম ও মিষ্ট ধ্বনি, 'খায়ে আসপা'", ModelStatus.READY, "jes_m", "jes_f"),
        DistrictDialect("narail", "নড়াইল", "Narail", "খুলনা বিভাগ", "Khulna Division", "চিত্রা পাড়ের সুর", "চিত্রা নদীর পাড়ে এস এম সুলতানের ছবি আঁকা দেখতে চলো যাই।", "চিত্রা নদীর পাড়ে এস এম সুলতানের ছবি আঁকা দেখতে চলো যাই।", "শান্ত টান", ModelStatus.COMING_SOON, "narail_m", "narail_f"),
        DistrictDialect("magura", "মাগুরা", "Magura", "খুলনা বিভাগ", "Khulna Division", "মধুমতী পার্শ্ববর্তী", "কাজটা শেষ করে ফেলাও, দেরি করলি তো সমস্যা হবা।", "কাজটি শেষ করে ফেলো, দেরি করলে তো সমস্যা হবে।", "'করলি', 'হবা'", ModelStatus.COMING_SOON, "magura_m", "magura_f"),
        DistrictDialect("jhenaidah", "ঝিনাইদহ", "Jhenaidah", "খুলনা বিভাগ", "Khulna Division", "কপোতাক্ষ আঞ্চলিক", "কপোতাক্ষের জলে শীতল হাওয়া বয়, মন ভরে যায়।", "কপোতাক্ষের জলে শীতল হাওয়া বয়ে যায়, মন ভরে যায়।", "মধুর কথ্য টান", ModelStatus.COMING_SOON, "jhen_m", "jhen_f"),
        DistrictDialect("kushtia", "কুষ্টিয়া", "Kushtia", "খুলনা বিভাগ", "Khulna Division", "লালন ভূমি / পদ্মা পার", "খাঁচার ভিতর অচিন পাখি কেমনে আসে যায়, লালনের গান শুনতে আসপা?", "খাঁচার ভিতর অচিন পাখি কেমনে আসে যায়, লালনের গান শুনতে আসবে?", "দার্শনিক গম্ভীর সুর", ModelStatus.READY, "kush_m", "kush_f"),
        DistrictDialect("chuadanga", "চুয়াডাঙ্গা", "Chuadanga", "খুলনা বিভাগ", "Khulna Division", "মাথাভাঙ্গা উপকূলীয়", "পান আর তামাকের দেশে তোমারে স্বাগতম জানাই গো!", "পান আর তামাকের দেশে তোমাকে স্বাগতম জানাই!", "পশ্চিমাঞ্চলীয় মিষ্টি টান", ModelStatus.COMING_SOON, "chua_m", "chua_f"),
        DistrictDialect("meherpur", "মেহেরপুর", "Meherpur", "খুলনা বিভাগ", "Khulna Division", "মুজিবনগর আঞ্চলিক", "মুজিবনগরের মাটিতে দেশের স্বাধীনতা ঘোষণা হইছিলো।", "মুজিবনগরের মাটিতে দেশের স্বাধীনতা ঘোষণা হয়েছিল।", "ঐতিহাসিক গাম্ভীর্য", ModelStatus.COMING_SOON, "meher_m", "meher_f"),

        // BARISAL DIVISION (6)
        DistrictDialect("barisal", "বরিশাল", "Barisal", "বরিশাল বিভাগ", "Barisal Division", "বরিশাইল্লা ভাষা", "মোগো বরিশালে আইবেন নাকি? আমড়া আর নারিকেলের ক্ষীর খাওয়ামু!", "আমাদের বরিশালে আসবেন নাকি? আমড়া আর নারিকেলের ক্ষীর খাওয়াব!", "'মোগো' (আমাদের), 'মোরা' (আমরা)", ModelStatus.READY, "bari_m", "bari_f", "মোগো (আমাদের), মোরা (আমরা), মনু"),
        DistrictDialect("bhola", "ভোলা", "Bhola", "বরিশাল বিভাগ", "Barisal Division", "দ্বীপ জেলার সুর", "মেঘনা নদীর তাজা ইলিশ আর মহিষের দধি খাইয়েন কিন্তু!", "মেঘনা নদীর তাজা ইলিশ আর মহিষের দই খাবেন কিন্তু!", "নদীমাতৃক দ্বীপের সুর", ModelStatus.READY, "bhola_m", "bhola_f"),
        DistrictDialect("patuakhali", "পটুয়াখালী", "Patuakhali", "বরিশাল বিভাগ", "Barisal Division", "কুয়াকাটা উপকূলীয়", "কুয়াকাটা যাইয়া সাগরকন্যা দেইখা মোরা অবাক হইছি!", "কুয়াকাটা গিয়ে সাগরকন্যা দেখে আমরা অবাক হয়েছি!", "'মোরা', সাগর পারের সুর", ModelStatus.READY, "patua_m", "patua_f"),
        DistrictDialect("pirojpur", "পিরোজপুর", "Pirojpur", "বরিশাল বিভাগ", "Barisal Division", "ভাসমান পেয়ারা হাট", "নৌকায় কইরা পেয়ারার বাজারে চলো মনু, কি সুন্দর দৃশ্য!", "নৌকা করে পেয়ারার বাজারে চলো ভাই, কী সুন্দর দৃশ্য!", "'মনু', নৌকার দোলের ছন্দ", ModelStatus.COMING_SOON, "piroj_m", "piroj_f"),
        DistrictDialect("barguna", "বরগুনা", "Barguna", "বরিশাল বিভাগ", "Barisal Division", "বিষখালী আঞ্চলিক", "হরিণঘাটার বনে হরিণ দেখতে যাইবেন মনু?", "হরিণঘাটার বনে হরিণ দেখতে যাবেন ভাই?", "উপকূলীয় আন্তরিক সুর", ModelStatus.COMING_SOON, "barguna_m", "barguna_f"),
        DistrictDialect("jhalokati", "ঝালকাঠি", "Jhalokati", "বরিশাল বিভাগ", "Barisal Division", "সুগন্ধা তীরবর্তী", "সুগন্ধা নদীর বাতাসে মন জুড়াইয়া যায় গো!", "সুগন্ধা নদীর বাতাসে মন জুড়িয়ে যায়!", "মিষ্ট দক্ষিণাঞ্চলীয় সুর", ModelStatus.COMING_SOON, "jhalo_m", "jhalo_f"),

        // SYLHET DIVISION (4)
        DistrictDialect("sylhet", "সিলেট", "Sylhet", "সিলেট বিভাগ", "Sylhet Division", "সিলেটি ভাষা", "ভালা আছেন নি ভাই? আমরার সিলেটি সাতকড়া দিয়া গরুর গোস্ত খাইছেন নি?", "ভালো আছেন কি ভাই? আমাদের সাতকড়া দিয়ে গরুর মাংস খেয়েছেন কি?", "সিলেটি নাগরী ও স্বতন্ত্র টোন, 'নি'", ModelStatus.READY, "syl_m", "syl_f", "ভালা (ভালো), আমরার (আমাদের)"),
        DistrictDialect("moulvibazar", "মৌলভীবাজার", "Moulvibazar", "সিলেট বিভাগ", "Sylhet Division", "চা বাগান আঞ্চলিক", "শ্রীমঙ্গলের চা বাগান আর মাধবকুণ্ডের ঝর্ণা দেখতে যাইমু!", "শ্রীমঙ্গলের চা বাগান আর মাধবকুণ্ডের ঝর্ণা দেখতে যাব!", "'যাইমু', 'আইমু'", ModelStatus.READY, "moulvi_m", "moulvi_f"),
        DistrictDialect("habiganj", "হবিগঞ্জ", "Habiganj", "সিলেট বিভাগ", "Sylhet Division", "খোয়াই পারের সুর", "শাহজীবাজারের গ্যাসক্ষেত্র আর পাহাড়িয়া অঞ্চল ঘুইরা আইলাম।", "শাহজীবাজারের গ্যাসক্ষেত্র আর পাহাড়িয়া অঞ্চল ঘুরে এলাম।", "সিলেটি ও ভাটি মিলন", ModelStatus.COMING_SOON, "habi_m", "habi_f"),
        DistrictDialect("sunamganj", "সুনামগঞ্জ", "Sunamganj", "সিলেট বিভাগ", "Sylhet Division", "ভাটির বাউল সুর", "আঁখি মুঞ্জিয়া দেখো রে রূপ কেমন চমৎকার! টাঙ্গুয়ার হাওরে যাইবায় নি?", "চোখ বন্ধ করে দেখো রূপ কেমন সুন্দর! টাঙ্গুয়ার হাওরে যাবে কি?", "বাউল গানের মরমি সুর", ModelStatus.READY, "suna_m", "suna_f"),

        // RANGPUR DIVISION (8)
        DistrictDialect("rangpur", "রংপুর", "Rangpur", "রংপুর বিভাগ", "Rangpur Division", "রংপুরিয়া / ভাওয়াইয়া", "ওরে মোর ভাই, কাইঙ্কা আছো? হামরা তো খুব ভালোই আছি রে!", "ওরে আমার ভাই, কেমন আছো? আমরা তো খুব ভালোই আছি!", "'হামরা' (আমরা), 'হামার', 'কাইঙ্কা', 'বাহে'", ModelStatus.READY, "rang_m", "rang_f", "হামরা (আমরা), কোন্ঠে (কোথায়), বাহে"),
        DistrictDialect("dinajpur", "দিনাজপুর", "Dinajpur", "রংপুর বিভাগ", "Rangpur Division", "কান্তর জিউ রূপ", "লিচু আর কাটারিভোগ চাউল নিয়া হামার দেশে আসো বাহে!", "লিচু আর কাটারিভোগ চাল নিয়ে আমাদের এলাকায় আসো ভাই!", "'বাহে', সরল উচ্চারণ", ModelStatus.READY, "dinaj_m", "dinaj_f"),
        DistrictDialect("gaibandha", "গাইবান্ধা", "Gaibandha", "রংপুর বিভাগ", "Rangpur Division", "তিস্তা-ব্রহ্মপুত্র চর", "চরের বালুতে মেলা বসছে গো, চলো দেইখা আসি।", "চরের বালুতে মেলা বসেছে, চলো দেখে আসি।", "'দেইখা আসি'", ModelStatus.COMING_SOON, "gai_m", "gai_f"),
        DistrictDialect("kurigram", "কুড়িগ্রাম", "Kurigram", "রংপুর বিভাগ", "Rangpur Division", "ধরলা তীরবর্তী", "ও কি গাড়িয়াল ভাই, কত রব আমি পন্থের পানে চাইয়া রে!", "ওহে গাড়োয়ান ভাই, কতক্ষণ আমি পথের দিকে তাকিয়ে থাকব!", "ভাওয়াইয়ার করুণ সুর", ModelStatus.READY, "kuri_m", "kuri_f"),
        DistrictDialect("lalmonirhat", "লালমনিরহাট", "Lalmonirhat", "রংপুর বিভাগ", "Rangpur Division", "তিস্তা ব্যারেজ রূপ", "তিস্তার পাড়ে নির্মল বাতাস খাইতে বহুত শান্তি লাগবার ধরছে।", "তিস্তার পাড়ে নির্মল বাতাস খেতে অনেক শান্তি লাগছে।", "'লাগবার ধরছে'", ModelStatus.COMING_SOON, "lal_m", "lal_f"),
        DistrictDialect("nilphamari", "নীলফামারী", "Nilphamari", "রংপুর বিভাগ", "Rangpur Division", "নীলসাগর কথ্য", "সৈয়দপুরের মিষ্টি আর নীলসাগরের দৃশ্য দেখতে ভুলিও না!", "সৈয়দপুরের মিষ্টি আর নীলসাগরের দৃশ্য দেখতে ভুলো না!", "'ভুলিও না'", ModelStatus.COMING_SOON, "nil_m", "nil_f"),
        DistrictDialect("panchagarh", "পঞ্চগড়", "Panchagarh", "রংপুর বিভাগ", "Rangpur Division", "হিমালয় পাদদেশীয়", "তেঁতুলিয়া যাইয়া কাঞ্চনজঙ্ঘার শুভ্র বরফ দেখলাম বাহে!", "তেঁতুলিয়া গিয়ে কাঞ্চনজঙ্ঘার সাদা বরফ দেখলাম ভাই!", "উত্তরের হিমেল টান ও 'বাহে'", ModelStatus.READY, "panch_m", "panch_f"),
        DistrictDialect("thakurgaon", "ঠাকুরগাঁও", "Thakurgaon", "রংপুর বিভাগ", "Rangpur Division", "টাঙ্গন অববাহিকা", "আম কাটার মতন মিষ্টি হাসি দিয়া কথা কও ক্যানে?", "আম কাটার মতো মিষ্টি হাসি দিয়ে কথা বলো কেন?", "'ক্যানে', 'কও'", ModelStatus.COMING_SOON, "thak_m", "thak_f"),

        // MYMENSINGH DIVISION (4)
        DistrictDialect("mymensingh", "ময়মনসিংহ", "Mymensingh", "ময়মনসিংহ বিভাগ", "Mymensingh Division", "ময়মনসিংহিয়া কথ্য", "কই গো ভাই, খাইছো নি ভাত? ব্রহ্মপুত্রের চরে ঘুরতে যাবা?", "কোথায় হে ভাই, ভাত খেয়েছ কি? ব্রহ্মপুত্রের চরে ঘুরতে যাবে?", "গীতিকার টান, 'খাইছো নি', 'যাবা'", ModelStatus.READY, "mymen_m", "mymen_f", "নি (প্রশ্নবোধক), আহো (এসো)"),
        DistrictDialect("jamalpur", "জামালপুর", "Jamalpur", "ময়মনসিংহ বিভাগ", "Mymensingh Division", "যমুনা অববাহিকা", "জামালপুরের পিঠা আর ছানার মিষ্টি খাইয়া যাও!", "জামালপুরের পিঠা আর ছানার মিষ্টি খেয়ে যাও!", "নরম ও সুরেলা টান", ModelStatus.READY, "jamal_m", "jamal_f"),
        DistrictDialect("sherpur", "শেরপুর", "Sherpur", "ময়মনসিংহ বিভাগ", "Mymensingh Division", "গারো পাহাড় পাদদেশীয়", "পাহাড়ের চূড়ায় উঠলে মেঘ ছুঁইয়া দেওয়া যায় গো!", "পাহাড়ের চূড়ায় উঠলে মেঘ ছুঁয়ে দেওয়া যায়!", "প্রতিধ্বনিময় সুর", ModelStatus.COMING_SOON, "sher_m", "sher_f"),
        DistrictDialect("netrokona", "নেত্রকোনা", "Netrokona", "ময়মনসিংহ বিভাগ", "Mymensingh Division", "সুসং দুর্গাপুর সুর", "বিজয়পুরের চিনামাটির পাহাড়ে মন কেমন উদাস হইয়া যায় রে!", "বিজয়পুরের চিনামাটির পাহাড়ে মন কেমন উদাস হয়ে যায়!", "লোকগীতির আবেগপূর্ণ সুর", ModelStatus.READY, "netro_m", "netro_f")
    )

    val divisions: List<String> = listOf(
        "সব বিভাগ (All)",
        "ঢাকা বিভাগ",
        "চট্টগ্রাম বিভাগ",
        "রাজশাহী বিভাগ",
        "খুলনা বিভাগ",
        "বরিশাল বিভাগ",
        "সিলেট বিভাগ",
        "রংপুর বিভাগ",
        "ময়মনসিংহ বিভাগ"
    )
}
