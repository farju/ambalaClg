package in.ace.pardeep.org.acev2;

/**
 * Created by hp 8 on 25-03-2016.
 */
public class AboutUsListContent {

    private String title;
    private int imageId;
    private String qualificationId;
    private String descriptionId;

    public static final AboutUsListContent[] aboutUsListContent={
        new AboutUsListContent("History",R.drawable.ace_ic_bckg,"","With a view to make available enormous opportunities for the rural masses in higher and technical education, Honorable Dr Jai Dev, a social reformer in true sense, vowed to establish an ambitious project of an engineering college at this interior, but a well connected place to Ambala. Ambala College of Engineering and Applied Research (ACE) has been established in the year 2002 by Shri Ram Swarup Memorial Trust. The institute is affiliated to Kurukshetra University, Kurukshetra, Haryana, India. Ambala College of Engineering and Applied Research (ACE) is managed by Shri Ram Swarup Memorial Trust, the Trustees include Engineers with Masters and Doctorate degrees from renowned Universities in USA, such as University of California, Berkeley, Santa Clara University and Polytechnic University of New York. They have a vast experience in the field of education and high-tech entrepreneurship. The college campus is spread over an area of 27 acres and has built-up area of 22296.30 Sqm (Including 4-Academic Blocks, 2-Boys Hostel,2-Girls Hostel,1-Director's Residence, 1- Guest house and 4- Faculty's Residence Blocks, Guest House, Student Activity Centre and Canteen. For economic growth and prosperity, the need is to produce professional engineers by imparting quality education to the students. Guided and graded classroom instructions, interactive tutorial sessions, hands-on laboratory experience, interaction with successful entrepreneurs and visiting faculty will help the students to develop self-confidence to face the future and turn the problems into opportunities. This is the primary objective at ACE besides granting a degree. The vision is for educating a 'complete person' for a life of service and leadership by focusing on a value-oriented curriculum, dedicated to educating students for competence, conscience and compassion. When Ambala College of Engineering and Applied Research (ACE) was started, it was often said that applied research was not possible at B.Tech level, especially in the new institutes. However, the vision of the founders was very clear that this is the need of the hour. Unless a change in the direction of engineering education was brought about, the quality of engineers in the country will not improve."),
        new AboutUsListContent("Mission & Vision",R.drawable.ace_ic_bckg,"","Institute Mission \nTo impart quality engineering education to students through quality teaching, hands on training, and applied research in practical and product oriented projects. \nTo impart such education that passing out students are ready with good theoretical and practical knowledge to suite the current need of industry. \nTo expose students to applied research, especially the fact that research does not require much money but does require great persistence.\nTo sow the seed of entrepreneurship in them so that our engineers become job providers and not job seekers. \nTo train students as a complete person through extracurricular activities and with an exposure to a transparent system based on ethics so that they believe that a successful institution and a successful business can be run with ethics without corruption.\n \nInstitute Vision \nTo become a source of technology and start an Incubation Centre for entrepreneurs resulting in this region developing into a vibrant industrial hub with many startup companies dealing with new technology"),
        new AboutUsListContent("Director",R.drawable.ashawantgupta,"Dr.Ashwant Gupta\n Ph.D. Electrical Engineering Ex.Adjunct Lecturer Santa Clara University,California,USA","In 2002,Dr. Ashawant helped found Ambala College of Engineering and Applied Research (ACE).Dr. Ashawant Gupta received the Ross Tucker Master's Memorial Award in 1991 for his Master's Thesis from the American Institute of Mining, Metallurgical, and Petroleum Engineers. He has been a Volunteer for the Bonded Labor Liberation Front, New Delhi and Group leader for the Local Chapter of The Hunger Project, Banagalore. He was Vice President of the Association of Graduate Engineering Students, Santa Clara University.He has published and presented over 20 papers in international journals and conferences."),
                    new AboutUsListContent("Principal",R.drawable.vprinci,"Dr. Amit Wason\nDoctorate in 2011 from Thapar University, Patiala.\n(B. Tech) in 2001\n Post graduated (M. Tech.) in 2005","\n" +
                    "\n" +
                    "ACE, over 12 years, has served the society and has carved a niche for itself as a pioneer institute & has been nourishing the industry with the intellectual resources its produces. The institute has always been striving to cater to the most predominant needs of the industry by providing a discerning balance of academic precision and practical prudence.\n" +
                    "\n" +
                    "The business prospectives are changing rapidly at a very fast pace and ACE has been rehabilitating its self to suit these needs. ACE has been functioning with a noble vision and mission clearly reflecting its social responsibility and commitment to nation building. The mission of ACE is \"to develop the entrepreneurs who are job providers not job seekers\". Faculty members here at ACE are making a mark with some very good teaching and with research both theoretical and experimental. Most of the faculty members at ACE have published a number of Research papers in the International/National Journals and conferences.\n" +
                    "\n" +
                    "On behalf of the Institute, I welcome you to ACE, a place where we celebrate youth and excellence and attempt to transform young persons into adults with a sense of social responsibility, human values and concern for environment. We hope that you would use this opportunity to realize your full potential and bring out the best in you. We are sure that the excellent academic environment and the opportunity to participate in applied research activities will also help you not only in developing your personality and in your all-round development but your intellectual level will also be enhanced\n" +
                    "\n" +
                    "I sincerely hope that your academic pursuit at ACE will be fruitful and enjoyable in every aspect and the experiences you gain here and the moments you spend here will be cherished by you all your life\n" +
                    "\n" +
                    "Wishing you the very best\n" +
                    "\n" +
                    "(Dr. Amit Wason)\n")
    };

    public AboutUsListContent(String title, int imageId, String qualification,String description) {
        this.descriptionId = description;
        this.imageId = imageId;
        this.qualificationId = qualification;
        this.title = title;
    }

    public static AboutUsListContent[] getAboutUsListContent() {
        return aboutUsListContent;
    }

    public String getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(String descriptionId) {
        this.descriptionId = descriptionId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(String qualificationId) {
        this.qualificationId = qualificationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
