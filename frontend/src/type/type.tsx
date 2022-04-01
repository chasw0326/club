type fetchState = {
  loading: boolean;
  data: any | clubItem[];
  error: any;
};

type SignUp = {
  email: string;
  password: string;
  name: string;
  nickname: string;
  university: string;
  profile: any;
  introduction: string;
};

type clubItem = {
  title: string;
  description: string;
  personnel: number;
  location: string;
};

type myClub = {
  club: string;
  address: string;
  universitiy: string;
  description: string;
};

type myPost = {
  nickname: string;
  title: string;
  content: string;
  createdAt: string;
};

type myComment = {
  title: string;
  nickname: string;
  content: string;
  createdAt: string;
};
const init = [
  {
    club: '',
    address: '',
    universitiy: '',
    description: '',

    nickname: '',
    title: '',
    content: '',

    commentTitle: '',
    commentNickname: '',
    commentContent: '',
  },
];

export type { fetchState, SignUp, clubItem, myClub, myPost, myComment };

export { init };
