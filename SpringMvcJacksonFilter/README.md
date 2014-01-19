Hi

I like Jackson JSON Processor. It is simple and powerful tool.

But It is lack of dynamic filtering, especially bi-directional references(or infinite loop).

After 2.0, Jackson support @JsonIdentityInfo, but generated json is not usable with a web browser. It is only usable when  Jackson-to-jackson situation.

Jackson support also @JsonIgnore. Simple case, @JsonIgnore is OK. But when a java object is render to many forms , @JsonIgnore is not usable.

Jackson support @JsonView. But in my thought, presentation layer  take responsibility for render json of object, not Model. @JsonView is  intrusive.      

So I make a jackson filter plugin to dynamic filter, and integration with spring mvc(presentation layer).
  
