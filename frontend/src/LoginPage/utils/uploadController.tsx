import { profile } from 'console';
import React from 'react';
import { SignUp } from '../../type/type';

const uploadController = (signInfo: SignUp) => {
  const formData = new FormData();

  formData.append('email', signInfo.email);
  formData.append('password', signInfo.password);
  formData.append('name', signInfo.name);
  formData.append('nickname', signInfo.nickname);
  formData.append('university', signInfo.university);
  formData.append('profile', signInfo.profile);
  formData.append('introduction', signInfo.introduction);

  return formData;
};

export { uploadController };
