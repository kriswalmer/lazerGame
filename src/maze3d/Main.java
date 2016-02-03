package maze3d;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Main extends SimpleApplication {

    static Dimension screen;
    BitmapText text;
    int count = 0;
    final int numOfBuggers = 24  ; 
    float tpfSum;
    Node sNode, cameraTarget;
    Material mat, mat1 , mat2, mat3 , matOrange, matArrow;
    Geometry geomLarge, geomSmall, geomGround , sphereMos;
    Geometry geoMosqui, geoMosqui2 , geoMosqui3,
            lazer ,  geomArrow ,geomArrow2,sphereZapper;
    Geometry  geoArray[]  = new Geometry[numOfBuggers];
   

    public static void main(String[] args) {
        Main app = new Main();
        initAppScreen(app);
        app.start();
    }
   

    @Override
    public void simpleInitApp() {
        initGui();
        initMaterial();
        initObjects();
        initLightandShadow();
        initCam();
        //
       
        buildZapper(40, 30 );
    }

    // -------------------------------------------------------------------------
    private void buildZapper(int cols, int rows ) {
       
    
    
                          
        rootNode.attachChild(sphereZapper); 
        sphereZapper.setLocalTranslation( 0 , 0 , 0 );
        
       Mosqui m  = new Mosqui();
           
        // set color of material to blue
        geoMosqui2.setMaterial(matOrange);
        
        geoMosqui.setMaterial(mat2);                   // set the cube's material
        
        rootNode.attachChild(geoMosqui2);
        rootNode.attachChild(geoMosqui);
        geoMosqui.setLocalTranslation( 38 , 0, 0 );
        geoMosqui2.setLocalTranslation(-38f, 0 ,0 );
        geoMosqui3.setMaterial(mat2);
        
        while (count < numOfBuggers )
        {   Sphere mos = new Sphere(32 ,32 ,3);
            geoArray[count] = new Geometry("Sphere" , mos) ;
            geoArray[count].setMaterial(mat2);
             
        
         rootNode.attachChild(geoArray[count]);
      geoArray[count].setLocalTranslation(FastMath.rand.nextFloat() *20 , FastMath.rand.nextFloat()*20 , FastMath.rand.nextFloat()*20 );
  count++;
        }
    
    }

  
  
    @Override
    public void simpleUpdate(float tpf  ) {
        
       // createMosquis(tpf); 
         Mosqui m = new Mosqui();
      geoMosqui = m.MosUpdate(tpf, geoMosqui) ; 
        geoMosqui2 = m.MosUpdate(tpf, geoMosqui2);
        count = 0 ; 
        while (count < numOfBuggers )
       {
       geoArray[count] = m.MosUpdate(tpf, geoArray[count]);
       
       count++; 
       }
          float timeOfFire = 0f ;
        
          float timeOfFireArray = 0f ;
          float tpfCount = tpf ; 
          
          float tpfCountArray = tpf ;
            boolean mosNeedsZapped=false;
            
            boolean mosZap[] = new boolean[numOfBuggers] ;
            count = 0 ; 
            while(count < numOfBuggers ){
                mosZap[count] = false ; 
            if(geoArray[count].getWorldTranslation(). x > 25 
                    ||(geoArray[count].getWorldTranslation(). x < -25) 
                    ||(geoArray[count].getWorldTranslation(). y < -25)
                    ||(geoArray[count].getWorldTranslation(). y > 25 ))
            {
            mosZap[count] = true ; 
            
            }
            count++;
            }
            if ((geoMosqui.getWorldTranslation().x > 30 )) 
                     
            {mosNeedsZapped  = true ; }
            count = 0; 
            while (count < 5 ){
                    if (mosZap[count] == true )
                    {
                    Lazer l =  new Lazer();
             rootNode.attachChild(geomArrow);
             l.Firelazer(geomArrow , geoArray[count].getLocalTranslation());
         
         mosZap[count] = false ; 
         timeOfFireArray = tpf ;
                    
                    }
            count++ ; }
                        
        if (mosNeedsZapped)
         { 
             Lazer l =  new Lazer();
             rootNode.attachChild(geomArrow);
             l.Firelazer(geomArrow , geoMosqui.getLocalTranslation());
         
         mosNeedsZapped= false ; 
         timeOfFire = tpf ; 
         
         }
        
        
        
        else{}
        count = 0 ; 
        while(count < numOfBuggers ){
         if ( (tpfCount - 1f ) > timeOfFire ){
            
         rootNode.detachChild(geomArrow);
         geoMosqui.setLocalTranslation(28 , 0, 0 );
         tpfCount = 0 ;
        }
        
        else if ((tpfCountArray -1f ) > timeOfFireArray)
        {
            rootNode.detachChild(geomArrow);
         geoArray[count].setLocalTranslation(-28 , 0, 0 );
         
         tpfCountArray = 0 ;
        }
        else {
            tpfCount += tpf*2 ; 
           
            tpfCountArray += tpf*2 ; 
        
        
       }
         count++; 
        }
    }

    // -------------------------------------------------------------------------
    // Inits
    // -------------------------------------------------------------------------
    private static void initAppScreen(SimpleApplication app) {
        AppSettings aps = new AppSettings(true);
        screen = Toolkit.getDefaultToolkit().getScreenSize();
        screen.width *= 0.75;
        screen.height *= 0.75;
        aps.setResolution(screen.width, screen.height);
        app.setSettings(aps);
        app.setShowSettings(false);
    }
    private void initObjects() 
    {
    
          Sphere mos = new Sphere( 32 ,32 , 3) ;
          Sphere mos2 = new Sphere( 32 ,32 , 3) ;
         geoMosqui = new Geometry("Sphere", mos);  // create cube geometry from the shape
          int i = 0 ; 
          geoMosqui2 = new Geometry("Sphere", mos); 
         geoMosqui3 = new Geometry("Sphere", mos); 
       
        Arrow arrow = new Arrow(new Vector3f(1.0f, 0, 0));
        arrow.setLineWidth(5f);
        geomArrow = new Geometry("a", arrow);
        geomArrow.setMaterial(matArrow);
        
         Sphere zapper = new Sphere(32  ,32 , 15.01f  );
            
       sphereZapper = new Geometry("Sphere", zapper);  // create cube geometry from the shape
        sphereZapper.setMaterial(mat);
        
    }
    private void initMaterial() {
     
        mat1 = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat1.setBoolean("UseMaterialColors", true);
        mat1.setColor("Ambient", new ColorRGBA(0.5f, 0.25f, 0.05f, 1.0f));
        mat1.setColor("Diffuse", new ColorRGBA(0.5f, 0.25f, 0.05f, 1.0f));
        mat1.setColor("Specular", ColorRGBA.Gray);
        mat1.setFloat("Shininess", 2f); // shininess from 1-128
        mat2 = new Material(assetManager,
          "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        mat2.setColor("Color", ColorRGBA.Green);
        mat3 = new Material(assetManager,
          "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        mat3.setColor("Color", ColorRGBA.Blue);  
       matOrange = new Material(assetManager,
          "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        mat3.setColor("Color", ColorRGBA.Blue);
        matArrow = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        matArrow.setColor("Color", ColorRGBA.LightGray);
        mat = new Material(assetManager,
          "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        mat.setColor("Color", ColorRGBA.Red);
    
    }

    private void initGui() {
        setDisplayFps(true);
        setDisplayStatView(false);
    }

    private void initLightandShadow() {
        
    }

    private void initCam() {
        cam.setLocation(new Vector3f(1f, 180f, 6f));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
    }
}
