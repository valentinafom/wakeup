#!/usr/bin/env python
# coding: utf-8

# In[20]:


import pandas as pd
import datetime
import numpy as np
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import f1_score  #metric to eval classification
RS=42


# In[21]:


#let s make dataframe
data_df=pd.read_csv('days100.csv')
data_df.head(20)


# In[22]:


def day_to_int(day):
  """convert str of Day into int """
  
  if day == 'Monday':
    s='1'
  elif day == 'Thursday':
    s='2'    
  elif day == 'Wednesday':
    s='3'
  elif day == 'Tuesday':
    s='4'
  elif day == 'Friday':
    s='5'
  elif day == 'Saturday':
    s='6'
  elif day == 'Sunday':
    s='7'
  return int(s)


# In[23]:


day_to_int('Sunday')


# In[24]:


data_df['Days']=data_df['Days'].apply(day_to_int)   #convert days from str to int


# In[25]:


#classes unbalanced so we will use class_weights=balanced
data_df['Target'].value_counts()


# In[26]:


#let s divide our dataset into train and test
X_train, X_test, y_train, y_test=train_test_split(data_df[['Days']],
                                                  data_df[['Target']], shuffle = True,
                                                test_size=0.1,random_state=RS)


# In[27]:


# instantiate our RF Classifier
rf = RandomForestClassifier(
    n_jobs=-1, 
    n_estimators=150,
    class_weight='balanced', # balance classes
    max_depth=10, # shallow tree depth to prevent overfitting
    random_state=RS # set a seed for reproducibility
)


# In[28]:


# fit our model
rf.fit(X_train, y_train)


# In[ ]:





# In[35]:


print(X_test)


# In[34]:


rf.predict(X_test)


# In[36]:


prediction=rf.predict(X_test)


# In[37]:


prediction[0]


# In[39]:


#let s save our model in order to use it future
import pickle   #lib for saving object
filename = 'finalized_model_days01.pickle'
pickle.dump(rf, open(filename, 'wb'))

# This is example of code how to load saved model
# load the model from disk
# loaded_model = pickle.load(open(filename, 'rb'))
# prediction = loaded_model.predict(X_test)
# print(prediction)


# In[ ]:





# In[ ]:




