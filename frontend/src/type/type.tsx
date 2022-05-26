type fetchState = {
  loading: boolean;
  data: any | clubItem[];
  error: any;
};

type userInfo = {
  email: string;
  name: string;
  nickname: string;
  introduction: string;
  university: string;
};

type category = {
  id: number;
  name: string;
  description: string;
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

type clubInformation = {
  name: string;
  address: string;
  description: string;
  category: string;
};

type member = {
  joinState: string;
  name: string;
};

type clubInfo = {
  id: number;
  name: string;
  address: string;
  university: string;
  description: string;
  imageUrl: string;
  category: string;
  members: member[];
};

type clubItem = {
  id: number | null;
  name: string;
  address: string;
  university: string;
  description: string;
  imageUrl: string;
  category: string;
  clubMembers: number | null;
};

type myClub = {
  id: number;
  name: string;
  address: string;
  university: string;
  description: string;
  imageUrl: string;
  category: number;
};

type myPost = {
  profileUrl: string;
  nickname: string;
  title: string;
  content: string;
  createdAt: string;
  commentCnt: number;
};

type myComment = {
  postId: number;
  postTitle: string;
  postContent: string;
  commentData: {
    commentId: number;
    profileUrl: string;
    nickname: string;
    content: string;
    createdAt: string;
  };
};

type postInfo = {
  postId: number;
  profileUrl: null;
  nickname: string;
  title: string;
  content: string;
  createdAt: string;
  commentCnt: number;
};

type commentInfo = {
  commentId: number;
  profileUrl: string;
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

export type {
  clubInformation,
  fetchState,
  SignUp,
  clubItem,
  myClub,
  myPost,
  myComment,
  category,
  postInfo,
  commentInfo,
  clubInfo,
  userInfo,
};

export { init };
