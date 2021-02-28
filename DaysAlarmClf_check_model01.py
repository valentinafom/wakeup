#!/usr/bin/env python
# coding: utf-8

# In[40]:


import pandas as pd
import pickle
import numpy as np


# In[41]:


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


# In[42]:


day_to_int('Friday')


# In[52]:


#let s make dataframe
day='Monday'
data_df=pd.DataFrame({'day':[day]})
data_df.head()


# In[53]:


data_df['day']=data_df['day'].apply(day_to_int)   #convert days from str
data_df.head()


# In[54]:


# load the model from disk
loaded_model = pickle.load(open('finalized_model_days01.pickle', 'rb'))
prediction = loaded_model.predict(data_df)
print(prediction)


# In[ ]:




