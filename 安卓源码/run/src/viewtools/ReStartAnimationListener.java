package viewtools;

import android.view.View;
import android.view.animation.Animation;
    class ReStartAnimationListener implements Animation.AnimationListener {
    	
    	int count = 0 ;
    	View view ; 
        public View getView() {
			return view;
		}

		public void setView(View view) {
			this.view = view;
		}

		public void onAnimationEnd(Animation animation) {
        	if (count<3) {
        		count++;
        		animation.reset();
                animation.setAnimationListener(this);
                animation.start();
			}else {
				if(view!=null)
				view.setVisibility(View.GONE);
				count=0;
				
			}
            
        }

        public void setReset() {
			
			count=0;
		}

		public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub

        }

        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub

        }

    }