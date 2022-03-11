type fetchState = {
  loading: boolean;
  data: any;
  error: any;
};

type SignUp = {
  email: string;
  password: string;
  name: string;
  nickname: string;
  university: string;
  profile: any;
  introduce: string;
};

type clubItem = {
  title: string;
  description: string;
  personnel: number;
  location: string;
};

export type { fetchState, SignUp, clubItem };
